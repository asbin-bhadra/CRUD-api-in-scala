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

import java.util.UUID
import scala.collection.mutable.ListBuffer

class UserRepository extends DataAccessObject[User] {

  private val userList : ListBuffer[User] = ListBuffer.empty[User]

  override def add(user : User):Option[UUID]={
    userList +=user
    user.id
  }

  override def getUsers: List[User] = {
    userList.toList
  }

  override def getUserById(id: Option[UUID]): List[User] = {
    val userDetails = userList.filter(item=>item.id == id).toList
    if (userDetails != Nil){
      userDetails
    }
    else {
      throw new RuntimeException("User not found")
    }
  }

  override def update(id: Option[UUID], updatedUser: User): Boolean = {
    val userId: Option[UUID] = getUserId(id)
    val list = userList.filterNot(item=>item.id == id)
    list +=updatedUser.copy(id=userId)
    userList.clear()
    userList ++= list
    true
  }

  def getUserId(id:Option[UUID]): Option[UUID] ={
    var userId :Option[UUID]= None
    userList.foreach{
      userDetails=>{
        if(userDetails.id == id) userId = userDetails.id
      }
    }
    if (userId.equals(None)){
      throw new RuntimeException("User not found")
    }
    else {
      userId
    }
  }

  override def deleteUserById(id: Option[UUID]): Boolean = {
    // status flag is used to identify if user exist or not
    var status = false

    userList.foreach{
      userDetails =>
        if(userDetails.id == id){
          userList -= userDetails
          status = true
        }
    }
    if(status) status else throw new RuntimeException("User not found")
  }

  override def deleteAllUsers(): Boolean = {
    if(userList.nonEmpty){
      userList.remove(userList.length-1)
      true
    }
    else{
      false
    }
  }
}
