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

package sudoku
import scala.io.Source

object Main extends App {
  var a: String = null
  if (args.length == 0) {
    println("Missing input filename")
    System.exit(1)
  }
  a = solveSudoku(args(0))
  print(a)


  def solveSudoku(filename: String): String ={
    var m: Array[Array[Int]] = null
    var s: String = ""
    m = recurse(loadSudoku(filename), 0,0)

    if (m != null) {
      return m.map((x:Array[Int]) => x.mkString("")).mkString("\n")
    }
    else {
      return null
    }
  }

  def isValidMove(a: Array[Array[Int]], row: Int, col: Int, candidate: Int): Boolean = {
    var i = 0;
    var j = 0;
    var limi = 0;
    var limj = 0;
    do {
      if (a(row)(i) == candidate) {
        return false;
      }
      i = i + 1;
    } while (i < 9)
    i = 0;
    do {
      if (a(i)(col) == candidate) {
        return false;
      }
      i = i + 1;
    } while (i < 9)

    i = 3 * (row / 3);
    j = 3 * (col / 3);
    limi = i + 3
    limj = j + 3
    do {
      j = 3 * (col / 3);
      do {
        if (a(i)(j) == candidate) {
          return false;
        }
        j = j + 1;
      } while (j < limj)
      i = i + 1
    } while (i < limi)
    return true;
  }

  def bound(row: Int, col: Int): (Int, Int) = {
    if ((col + 1) > 8) {
      (row + 1, 0)
    } else {
      (row, col + 1)
    }
  }

  def recurse(a: Array[Array[Int]], row: Int, col: Int): Array[Array[Int]] = {
    var i = 1;
    var sol: Array[Array[Int]] = null

    if (a(row)(col) != 0) {
      val (myrow, mycol) = bound(row, col)
      if (myrow >= 9) {
        // solution found
        return a
      } else {
        return recurse(a, myrow, mycol)
      }
    } else {
      do {
        if (isValidMove(a, row, col, i)) {
          a(row)(col) = i
          sol = recurse(a, row, col)
          if (sol != null) {
            return a;
          }
        }
        i = i + 1
      } while (i < 10)
      a(row)(col) = 0
      return null;
    }
  }


  def loadSudoku(filename: String): Array[Array[Int]] = {
    val a = Array.ofDim[Int](9, 9)
    var i = 0;
    var form = (x: String) => if (x == "-") "0" else x;

    for (line <- Source.fromFile(filename).getLines) {
      if (i < 9) {
        a(i) = line.split("").map(form).map(_.toInt)
      }
      i = i + 1

    }
    return a
  }
}
