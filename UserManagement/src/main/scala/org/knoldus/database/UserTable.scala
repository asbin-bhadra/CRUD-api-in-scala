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

package org.knoldus.database

import org.knoldus.model.User

import java.util.UUID
import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

class UserTable {
  private val userList = ListBuffer.empty[User]

  def create(user : User): Future[Option[UUID]]= Future{
    Try(userList.append(user)) match{
      case Success(_) => userList.last.id
      case Failure(_) => throw new RuntimeException("Invalid request")
    }
  }

  def drop(id : Option[UUID]) : Future[Boolean] = Future{
    Try{
      val updatedList = userList.filterNot(_.id == id)
      if(updatedList.length == userList.length) throw new Exception
      userList.clear()
      userList ++= updatedList
    } match{
      case Success(_) => true
      case Failure(_) => false
    }
  }

  def dropAll: Future[Boolean]= Future{
    Try{
      if(userList.toList == Nil){
        throw new RuntimeException
      }
      else{
        userList.clear()
      }
    } match{
      case Success(_) => true
      case Failure(_) => false
    }
  }

  def get(id : Option[UUID]): Future[List[User]]= Future{
    Try{
      val userDeatils = userList.filter(_.id == id).toList
      if(userDeatils == Nil) {
        throw new RuntimeException
      } else {
        userDeatils
      }
    } match {
      case Success(value) => value
      case Failure(_) =>throw  new RuntimeException("User not found")
    }

  }

  def getAll:Future[List[User]]= Future{
    Try{
      userList.toList
    } match {
      case Success(value) => value
      case Failure(_) => throw new RuntimeException
    }
  }

  def update(id : Option[UUID], updatedUser : User) : Future[Boolean]= Future{
    Try{
      val tempUserList = userList.filterNot(_.id == id)
      if(tempUserList.length == userList.length){
        throw new RuntimeException
      }
      else{
        tempUserList += updatedUser
        userList.clear()
        userList ++= tempUserList
      }
    } match {
      case Success(_) => true
      case Failure(_) => false
    }
  }
}
