// Copyright (C) 2011-2012 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package org.knoldus.request

import org.knoldus.repository.DataAccessObject
import org.knoldus.model.User
import org.knoldus.validator.Validator

import java.util.UUID
import scala.concurrent.Future

class UserService(userRepository : DataAccessObject[User], validator: Validator) {

  def addUser(user : User): Future[Option[UUID]]={
    if(isUserValid(user)) userRepository.add(user.copy(id=Option(UUID.randomUUID()))) else throw new RuntimeException("Invalid user ID")
  }

  def getUsers: Future[List[User]] = userRepository.getUsers

  def getUserById(id : Option[UUID]): Future[List[User]] ={
    userRepository.getUserById(id)
  }

  def updateUser(id : Option[UUID], updatedUser: User): Future[Boolean]={
    if(isUserValid(updatedUser)) userRepository.update(id, updatedUser.copy(id=id)) else throw new RuntimeException("Invalid User ID")
  }

  def deleteUserById(id : Option[UUID]): Future[Boolean]={
    userRepository.deleteUserById(id)
  }

  def deleteAllUsers(): Future[Boolean]={
    userRepository.deleteAllUsers()
  }

  private def isUserValid(user : User): Boolean={
    user match{
      case User(None,_,_,_,_,_) =>  if(isEmailMobileNumberValid(user)) true else throw new RuntimeException("Email Id or mobile number is not valid")
      case User(Some(_),_,_,_,_,_) => false
    }
  }

  private def isEmailMobileNumberValid(user : User): Boolean ={
    if(validator.isEmailValid(user.email) && validator.isPhoneValid(user.mobileNumber)) true else false
  }

}
