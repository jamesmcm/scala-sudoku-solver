// Copyright (C) 2019 James McMurray
//
// scala-sudoku-solver is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// scala-sudoku-solver is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with scala-sudoku-solver.  If not, see <http://www.gnu.org/licenses/>.

import org.scalatest.FunSuite

class MainTest extends FunSuite {
      test("Main.recurse_evil") {
          val source = io.Source.fromFile("src/test/data/evil8104462993_solution.txt")
          val solution = try source.mkString finally source.close()
          assert(sudoku.Main.solveSudoku("src/test/data/evil8104462993.txt") === solution)
      }

  test("Main.recurse_hard") {
    val source = io.Source.fromFile("src/test/data/hard160820349_solution.txt")
    val solution = try source.mkString finally source.close()
    assert(sudoku.Main.solveSudoku("src/test/data/hard160820349.txt") === solution)
  }
}
