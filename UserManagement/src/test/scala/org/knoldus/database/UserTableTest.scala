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

import org.knoldus.model.{User, UserType}
import scala.concurrent.duration._
import org.scalatest.flatspec.AnyFlatSpec

import java.util.UUID
import scala.concurrent.Await

class UserTableTest extends AnyFlatSpec {
    val userTable = new UserTable
    val uuid: UUID = UUID.randomUUID()
    val user1: User = User(Option(UUID.randomUUID()), "rahul", "rahul1996@gmail.com", Option("Gurgaon"), "8724835905", UserType.Admin)
    val user2: User =User(Option(UUID.randomUUID()), "rahul1996", "rahul1996@gmail.com", Option("Gurgaon"), "8724835905", UserType.Admin)

    "add" should "return some uuid" in {
      val result = Await.result(userTable.create(user1), 5 seconds)
      assert(Some(result).nonEmpty)
      Await.result(userTable.dropAll, 5 seconds)
    }

    "getAll" should "return a List of users" in {
      Await.result(userTable.create(user1), 5 seconds)
      val result = Await.result(userTable.getAll, 5 seconds)
      assert(result.nonEmpty)
      Await.result(userTable.dropAll, 5 seconds)
    }
    it should "return empty list if no user exist" in {
      val result = Await.result(userTable.getAll, 5 seconds)
      assert(result.isEmpty)
    }

    "get" should "returns List" in {
      val userId1 = Await.result(userTable.create(user1), 5 seconds)
      val userId2 = Await.result(userTable.create(user2), 5 seconds)
      val result = Await.result(userTable.get(userId1), 5 seconds)
      assert(result==List(user1))
      Await.result(userTable.dropAll, 5 seconds)
    }
    it should "throw RuntimeException" in {
      assertThrows[RuntimeException] {
        Await.result(userTable.get(Option(UUID.randomUUID())), 5 seconds)
        Await.result(userTable.dropAll, 5 seconds)
      }
    }

    "update" should "return true" in {
      val userId1 = Await.result(userTable.create(user1), 5 seconds)
      val userId2 = Await.result(userTable.create(user2), 5 seconds)
      val result: Boolean = Await.result(userTable.update(userId2,user2.copy(userName = "Rahul1996")),5 seconds)
      assert(result)
      Await.result(userTable.dropAll, 5 seconds)
    }

    it should "return false" in {
      val userId1 = Await.result(userTable.create(user1), 5 seconds)
      val userId2 = Await.result(userTable.create(user2), 5 seconds)
      val result: Boolean = Await.result(userTable.update(Some(UUID.randomUUID()),user2.copy(userName = "Rahul1996")),5 seconds)
      assert(!result)
      Await.result(userTable.dropAll, 5 seconds)
    }

    "drop" should "returns true" in {
      val userId1 = Await.result(userTable.create(user1),5 seconds)
      val userId2 = Await.result(userTable.create(user2),5 seconds)
      val result: Boolean = Await.result(userTable.drop(userId1), 5 seconds)
      assert(result)
      Await.result(userTable.dropAll, 5 seconds)
    }
    it should "returns false" in {
      val result = Await.result(userTable.drop(Option(UUID.randomUUID())), 5 seconds)
      assert(!result)
      Await.result(userTable.dropAll, 5 seconds)
    }

    "drop" should "return true" in {
      Await.result(userTable.create(user1), 5 seconds)
      val result = Await.result(userTable.dropAll, 5 seconds)
      assert(result)
    }
    it should "return false" in {
      val result = Await.result(userTable.dropAll, 5 seconds)
      assert(!result)
    }
}
