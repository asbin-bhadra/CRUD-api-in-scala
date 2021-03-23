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

import org.knoldus.model.{User, UserType}
import org.scalatest.flatspec.AnyFlatSpec

import java.util.UUID

class UserRepositoryTest extends AnyFlatSpec{
  val userRepository = new UserRepository
  val uuid = UUID.randomUUID()
  val user1 = User(Option(UUID.randomUUID()), "rahul", "rahul1996@gmail.com", Option("Gurgaon"), "8724835905", UserType.Admin)
  val user2 =User(Option(UUID.randomUUID()), "rahul1996", "rahul1996@gmail.com", Option("Gurgaon"), "8724835905", UserType.Admin)

  "add" should "return some uuid" in {
    val uuid = UUID.randomUUID()
    val result: Option[UUID] = userRepository.add(user1)
    assert(Some(result).nonEmpty)
    userRepository.deleteAllUsers()
  }

  "getUser" should "return a List of users" in {
    userRepository.add(user1)
    val result: List[User] = userRepository.getUsers
    assert(result.nonEmpty)
    userRepository.deleteAllUsers()
  }
  it should "return empty list if no user exist" in {
    val result: List[User] = userRepository.getUsers
    assert(result.isEmpty)
  }

  "getUserById" should "returns List" in {
    val userId1 = userRepository.add(user1)
    val userId2 = userRepository.add(user2)
    val result: List[User] = userRepository.getUserById(userId1)
    assert(result==List(user1))
    userRepository.deleteAllUsers()


  }
  it should "throw RuntimeException" in {
    assertThrows[RuntimeException] {
      userRepository.getUserById(Option(UUID.randomUUID()))
      userRepository.deleteAllUsers()
    }
  }

  "update" should "return true" in {
    val userId1 = userRepository.add(user1)
    val userId2 = userRepository.add(user2)
    val result: Boolean = userRepository.update(userId2,user2.copy(userName = "Rahul1996"))
    assert(result)
    userRepository.deleteAllUsers()
  }

  "deleteUserById" should "returns true" in {
    val userId1 = userRepository.add(user1)
    val userId2 = userRepository.add(user2)
    val result: Boolean = userRepository.deleteUserById(userId1)
    assert(result)
    userRepository.deleteAllUsers()
  }
  it should "returns false" in {
    assert(!userRepository.deleteUserById(Option(UUID.randomUUID())))
    userRepository.deleteAllUsers()
  }

  "deleteAllUsers" should "return true" in {
    userRepository.add(user1)
    assert(userRepository.deleteAllUsers())
  }
  it should "return false" in {
    assert(!userRepository.deleteAllUsers())
  }
}
