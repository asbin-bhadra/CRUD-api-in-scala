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

package org.knoldus.repository

import org.knoldus.database.UserTable
import org.knoldus.model.User

import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class UserRepository(userTable : UserTable) extends DataAccessObject[User] {

  override def add(user : User):Future[Option[UUID]]= {
    userTable.create(user)
  }

  override def getUsers: Future[List[User]] = {
    userTable.getAll
  }

  override def getUserById(id: Option[UUID]): Future[List[User]] = {
    userTable.get(id)
  }

  override def update(id: Option[UUID], updatedUser: User): Future[Boolean] = {
    userTable.update(id,updatedUser)
  }

  override def deleteUserById(id: Option[UUID]): Future[Boolean] = {
    userTable.drop(id)
  }

  override def deleteAllUsers(): Future[Boolean] = {
    userTable.dropAll
  }
}
