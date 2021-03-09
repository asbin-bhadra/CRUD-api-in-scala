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

package org.knoldus.db

import org.knoldus.model.User
import org.knoldus.validator.Validator

import java.util.UUID
import scala.collection.mutable.ListBuffer

class UserRepository extends DataAccessObject[User] {

  // Initialize an empty ListBuffer
  val userList : ListBuffer[User] = ListBuffer.empty[User]
  val validator = new Validator

  // Add a new user
  override def add(user : User):UUID={
    // get a new random UUID for ID
    val uuid = UUID.randomUUID()
    user match{
      // If no user id found than assign a random UUID as a user id
      case User(None,_,_,_,_,_) => userList +=user.copy(id=Option(uuid));uuid
      // If custom ID received throw exception
      case User(Some(_),_,_,_,_,_) => throw new RuntimeException("Invalid user ID")
    }
  }

  override def getUsers: List[User] = {
    // If List is not empty return userList else throw RuntimeException
    if(userList.nonEmpty) userList.toList else throw new RuntimeException("No user found")
  }

  override def getUserById(id: UUID): List[User] = {
    val userDetails = userList.filter(userList => if(userList.id.get == id) true else false).toList
    if (userDetails != Nil){
      userDetails
    }
    else {
      throw new RuntimeException("User not found")
    }
  }

  override def updateUserName(id: UUID, newUserName: String): Boolean = {
    // status flag is used to identify if user exist or not
    var status : Boolean = false

    userList.zipWithIndex.foreach{
      case(userDetails,index) =>
        if(userDetails.id.get == id){
          // Update the username
          userList.update(index,userDetails.copy(userName = newUserName))
          status = true
        }

    }
    if(status) status else throw new RuntimeException("User not found")
  }

  override def deleteUserById(id: UUID): Boolean = {
    // status flag is used to identify if user exist or not
    var status = false

    userList.foreach{
      userDetails =>
        if(userDetails.id.get == id){
          // Remove user from the list
          userList -= userDetails
          status = true
        }
    }
    if(status) status else throw new RuntimeException("User not found")
  }

  override def deleteAllUsers(): Boolean = {
    if(userList.nonEmpty){
      // Remove all user from the list
      userList.remove(userList.length-1)
      true
    }
    else{
      // Throw exception if no user found
      throw new RuntimeException("No user found")
    }
  }

}
