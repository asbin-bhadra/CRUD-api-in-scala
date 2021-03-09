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

import org.knoldus.db.DataAccessObject
import org.knoldus.model.User
import org.knoldus.validator.Validator

import java.util.UUID

class UserService(userRepository : DataAccessObject[User]) {

  // Returns id of the user creted
  def addUser(user : User): UUID={
    val validator = new Validator

    // Check if email or mobile number valid or not
    if(validator.isEmailValid(user.email) && validator.isPhoneValid(user.mobileNumber)){
      userRepository.add(user)
    }
    else {
      // Throw exception if email or password is not valid
      throw new RuntimeException("Email Id or mobile number is not valid")
    }
  }

  // Returns list of user
  def getUsers: List[User] = userRepository.getUsers

  def getUserById(id : UUID): List[User] ={
    userRepository.getUserById(id)
  }

  def updateUserName(id : UUID, newUserName :  String): Boolean={
    userRepository.updateUserName(id,newUserName)
  }

  def deleteUserById(id : UUID): Boolean={
    userRepository.deleteUserById(id)
  }

  def deleteAllUsers(): Boolean={
    userRepository.deleteAllUsers()
  }

}
