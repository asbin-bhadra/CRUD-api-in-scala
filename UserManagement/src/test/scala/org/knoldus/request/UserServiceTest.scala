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

import org.knoldus.db.UserRepository
import org.knoldus.model.{User, UserType}
import org.knoldus.validator.Validator
import org.mockito.MockitoSugar.{mock, when}
import org.scalatest.flatspec.AnyFlatSpec

import java.util.UUID

class UserServiceTest extends AnyFlatSpec{
  val mockedValidator: Validator = mock[Validator]
  val mockedUserRepository : UserRepository = mock[UserRepository]
  val user: User = User(None,"asbin bhadra","asbin143@gmail.com",Some("Barpeta Road"),"7988313043",UserType.Customer)
  val userService = new UserService(mockedUserRepository,mockedValidator)


  "addUser" should "return id" in{
    when(mockedValidator.isEmailValid("asbin143@gmail.com")) thenReturn{true}
    when(mockedValidator.isPhoneValid("7988313043")) thenReturn{true}

    when(mockedUserRepository.add(user)) thenReturn {Option(UUID.randomUUID())}
    val result = userService.addUser(user)
    assert(Some(result).nonEmpty)
  }
  it should "through RuntimeException" in{
    when(mockedValidator.isEmailValid("asbin143@gmail.com")) thenReturn{true}
    when(mockedValidator.isPhoneValid("7988313043")) thenReturn{true}

    when(mockedUserRepository.add(user)) thenReturn {Option(UUID.randomUUID())}
    assertThrows[RuntimeException] {
      userService.addUser(user.copy(id = Some(UUID.randomUUID())))
    }
  }

  it should "through RuntimeException as email id invalid" in{
    when(mockedValidator.isEmailValid("asbin143@gmail.com")) thenReturn{false}
    when(mockedValidator.isPhoneValid("7988313043")) thenReturn{true}

    when(mockedUserRepository.add(user)) thenReturn {Option(UUID.randomUUID())}
    assertThrows[RuntimeException] {
      userService.addUser(user)
    }
  }

  it should "through RuntimeException as mobile number invalid" in{
    when(mockedValidator.isEmailValid("asbin143@gmail.com")) thenReturn{true}
    when(mockedValidator.isPhoneValid("7988313043")) thenReturn{false}

    when(mockedUserRepository.add(user)) thenReturn {Option(UUID.randomUUID())}
    assertThrows[RuntimeException] {
      userService.addUser(user)
    }
  }
  it should "through RuntimeException as mobile number and email id invalid" in{
    when(mockedValidator.isEmailValid("asbin143@gmail.com")) thenReturn{false}
    when(mockedValidator.isPhoneValid("7988313043")) thenReturn{false}

    when(mockedUserRepository.add(user)) thenReturn {Option(UUID.randomUUID())}
    assertThrows[RuntimeException] {
      userService.addUser(user)
    }
  }


  "getUser" should "return list of user" in{
    when(mockedUserRepository.getUsers) thenReturn {List(user)}
    val result:List[User] = userService.getUsers
    assert(result.nonEmpty)
  }

  it should "return empty list" in {
    when(mockedUserRepository.getUsers) thenReturn {List.empty}
    val result:List[User] = userService.getUsers
    assert(result.isEmpty)
  }

  "getUserById" should "return list of user" in {
    when(mockedUserRepository.getUserById(user.id)) thenReturn {List(user)}
    val result:List[User] = userService.getUserById(user.id)
    assert(result.nonEmpty)
  }

  it should "through RuntimeException" in {

    intercept[RuntimeException]{
      when(mockedUserRepository.getUserById(user.id)) thenThrow(throw new RuntimeException)
    }
    assertThrows[RuntimeException] {

      userService.getUserById(user.id)
    }
  }

  "updateUser" should "return true" in{
    when(mockedValidator.isEmailValid("asbin143@gmail.com")) thenReturn{true}
    when(mockedValidator.isPhoneValid("7988313043")) thenReturn{true}

    when(mockedUserRepository.update(user.id,user)) thenReturn {true}
    val result:Boolean = userService.updateUser(user.id,user)
    assert(result)
  }
  it should "through RuntimeException as email id invalid" in{
    when(mockedValidator.isEmailValid("asbin143@gmail.com")) thenReturn{false}
    when(mockedValidator.isPhoneValid("7988313043")) thenReturn{true}

    when(mockedUserRepository.update(user.id,user)) thenReturn {true}
    assertThrows[RuntimeException] {
      userService.updateUser(user.id,user)
    }
  }
  it should "through RuntimeException as mobile number invalid" in{
    when(mockedValidator.isEmailValid("asbin143@gmail.com")) thenReturn{true}
    when(mockedValidator.isPhoneValid("7988313043")) thenReturn{false}

    when(mockedUserRepository.update(user.id,user)) thenReturn {true}
    assertThrows[RuntimeException] {
      userService.updateUser(user.id,user)
    }
  }

  it should "through RuntimeException as email id and mobile number invalid" in{
    when(mockedValidator.isEmailValid("asbin143@gmail.com")) thenReturn{false}
    when(mockedValidator.isPhoneValid("7988313043")) thenReturn{false}

    when(mockedUserRepository.update(user.id,user)) thenReturn {true}
    assertThrows[RuntimeException] {
      userService.updateUser(user.id,user)
    }
  }
  it should "return false" in{
    when(mockedValidator.isEmailValid("asbin143@gmail.com")) thenReturn{true}
    when(mockedValidator.isPhoneValid("7988313043")) thenReturn{true}

    when(mockedUserRepository.update(user.id,user)) thenReturn {false}
    val result = userService.updateUser(user.id,user)
    assert(!result)
  }


  "deleteUserById" should "return true" in {
    when(mockedUserRepository.deleteUserById(user.id)) thenReturn {true}
    val result:Boolean = userService.deleteUserById(user.id)
    assert(result)
  }
  it should "return false" in {
    when(mockedUserRepository.deleteUserById(user.id)) thenReturn {false}
    val result:Boolean = userService.deleteUserById(user.id)
    assert(!result)
  }
  "deleteAllUser" should "return true" in {
    when(mockedUserRepository.deleteAllUsers()) thenReturn {true}
    val result:Boolean = userService.deleteAllUsers()
    assert(result)
  }
  it should "return false" in {
    when(mockedUserRepository.deleteAllUsers()) thenReturn {false}
    val result:Boolean = userService.deleteAllUsers()
    assert(!result)
  }

}
