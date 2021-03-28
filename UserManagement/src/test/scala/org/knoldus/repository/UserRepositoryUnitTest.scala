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
import org.knoldus.model.{User, UserType}
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AnyFlatSpec

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future}

class UserRepositoryUnitTest extends AnyFlatSpec {
  val mockedUserTable : UserTable = mock[UserTable]
  val user: User = User(None,"asbin bhadra","asbin143@gmail.com",Some("Barpeta Road"),"7988313043",UserType.Customer)
  val userRepository = new UserRepository(mockedUserTable)

  "add" should "add the user and return userid" in {
    when(mockedUserTable.create(user)) thenReturn Future(Option(UUID.randomUUID()))

    val result = Await.result(userRepository.add(user),5 seconds)
    assert(result.nonEmpty)
  }
  it should "not throws RuntimeException" in {

    intercept[RuntimeException]{
      when(Await.result(mockedUserTable.create(user),5 seconds)) thenThrow(throw new RuntimeException("Invalid operation"))
    }
    assertThrows[RuntimeException](Await.result(userRepository.add(user),5 seconds))
  }

  "getUsers" should "return list of users" in {
    when(mockedUserTable.getAll) thenReturn Future(List(user))

    val result = Await.result(userRepository.getUsers,5 seconds)
    assert(result.nonEmpty)
  }

  it should "return empty list as no user found" in {

    when(mockedUserTable.getAll) thenReturn Future(List())

    val result = Await.result(userRepository.getUsers,5 seconds)
    assert(result.isEmpty)
  }


  "getUserById" should "return the user details in a list" in {
    val uuid = UUID.randomUUID()
    when(mockedUserTable.get(Some(uuid))) thenReturn Future(List(user))

    val result = Await.result(userRepository.getUserById(Some(uuid)),5 seconds)
    assert(result.nonEmpty)
  }
  it should "throws RuntimeException as user not found" in {

    intercept[RuntimeException]{
      when(Await.result(mockedUserTable.get(user.id),5 seconds)) thenThrow(throw new RuntimeException)
    }

    assertThrows[RuntimeException](Await.result(userRepository.getUserById(user.id),5 seconds))
  }

  "update" should "true" in {
    when(mockedUserTable.update(user.id,user)) thenReturn Future(true)

    val result = Await.result(userRepository.update(user.id,user),5 seconds)
    assert(result)
  }

  it should "return false as username is not correct" in {
    when(mockedUserTable.update(user.id,user)) thenReturn Future(false)

    val result = Await.result(userRepository.update(user.id,user),5 seconds)
    assert(!result)
  }

  "deleteUserById" should "return true" in {
    val uuid = UUID.randomUUID()
    when(mockedUserTable.drop(Some(uuid))) thenReturn Future(true)

    val result = Await.result(userRepository.deleteUserById(Some(uuid)),5 seconds)
    assert(result)
  }
  it should "return false as user id is not valid" in {

    val uuid = UUID.randomUUID()
    when(mockedUserTable.drop(Some(uuid))) thenReturn Future(false)

    val result = Await.result(userRepository.deleteUserById(Some(uuid)),5 seconds)
    assert(!result)
  }

  "deleteAllUsers" should "return true" in {
    when(mockedUserTable.dropAll) thenReturn Future(true)

    val result = Await.result(userRepository.deleteAllUsers(),5 seconds)
    assert(result)
  }

  it should "return false as no user found" in {

    when(mockedUserTable.dropAll) thenReturn Future(false)

    val result = Await.result(userRepository.deleteAllUsers(),5 seconds)
    assert(!result)
  }


}
