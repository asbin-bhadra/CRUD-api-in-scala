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
import scala.util.{Failure, Success, Try}

class UserRepository extends DataAccessObject[User] {

  private val userList : ListBuffer[User] = ListBuffer.empty[User]

  override def add(user : User):Option[UUID]={
    userList.append(user)
    userList.last.id
  }

  override def getUsers: List[User] = {
    userList.toList
  }

  override def getUserById(id: Option[UUID]): List[User] = {
    val userDetails = getUserDetailsById(id)
    if (userDetails != Nil) userDetails else throw new RuntimeException("User not found")
  }

  override def update(id: Option[UUID], updatedUser: User): Boolean = {
    val index = getIndexById(id)
    if(index != -1) { userList.update(index,updatedUser); true } else false
  }

  override def deleteUserById(id: Option[UUID]): Boolean = {
    val index = getIndexById(id)
    if(index != -1) { userList.remove(index); true } else false
  }

  override def deleteAllUsers(): Boolean = {
    if(userList.nonEmpty) { userList.remove(0, userList.length); true } else false
  }

  private def getUserDetailsById(id : Option[UUID]):List[User]={
    userList.filter(item => item.id == id).toList
  }

  private def getIndexById(id:Option[UUID]):Int={
    val list = getUserDetailsById(id)
    if(list != Nil) userList.indexOf(list.head) else -1
  }
}
