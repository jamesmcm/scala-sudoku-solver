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

import org.scalatest.FunSuite

class MainTest extends FunSuite {
      test("Main.recurse_evil") {
          val solution = io.Source.fromFile("src/test/data/evil8104462993_solution.txt").mkString
          assert(sudoku.Main.solveSudoku("src/test/data/evil8104462993.txt") == solution)
      }

  test("Main.recurse_hard") {
    val solution = io.Source.fromFile("src/test/data/hard160820349_solution.txt").mkString
    assert(sudoku.Main.solveSudoku("src/test/data/hard160820349.txt") == solution)
  }
}
