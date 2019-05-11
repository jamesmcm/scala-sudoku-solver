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
  var a: String = ""
  if (args.length == 0) {
    throw new IllegalArgumentException("Missing input filename")
    System.exit(1)
  }
  a = solveSudoku(args(0))
  print(a)


  def solveSudoku(filename: String): String ={
    var m: Option[Array[Array[Int]]] = None
    m = recurse(loadSudoku(filename), 0,0)
    m match {
      case None => throw new IllegalStateException("No valid solution")
      case Some(solution) => solution.map((x:Array[Int]) => x.mkString("")).mkString("\n")
    }
  }

  def getBoxBounds(row: Int, col: Int): (Int, Int) = {
    (3*(row/3), 3*(col/3))
  }

  def isValidMove(a: Array[Array[Int]], row: Int, col: Int, candidate: Int): Boolean = {
    val (i, j) = getBoxBounds(row, col)
    !(a(row)
      .map((x: Int) => x == candidate)
      .foldLeft(false)((x:Boolean, y:Boolean) => x | y) |
    a.map((x: Array[Int]) => x(col))
      .map((x: Int) => x == candidate)
      .foldLeft(false)((x:Boolean, y:Boolean) => x | y) |
    a.slice(i, i + 3).flatMap((x: Array[Int]) => x.slice(j, j + 3))
      .map((x: Int) => x == candidate)
      .foldLeft(false)((x:Boolean, y:Boolean) => x | y))
  }

  def bound(row: Int, col: Int): (Int, Int) = {
    (row, col) match {
      case (_, 8) => (row + 1, 0)
      case _ => (row, col + 1)
    }
  }

  def move(a: Array[Array[Int]], row: Int, col: Int): Option[Array[Array[Int]]] = {
    val (newrow, newcol) = bound(row, col)
    (newrow, newcol) match {
      case (9, _) => Some(a)
      case _ => recurse(a, newrow, newcol)
    }
  }

  def substitute(a: Array[Array[Int]], row: Int, col: Int, candidate: Int): Option[Array[Array[Int]]] = {
    var sol: Option[Array[Array[Int]]] = None
    val b = a.map(_.clone)

    b(row)(col) = candidate
    sol = recurse(b, row, col)
    sol
  }

  def recurse(a: Array[Array[Int]], row: Int, col: Int): Option[Array[Array[Int]]] = {
    a(row)(col) match {
      case 0 => (1 to 9).filter(isValidMove(a, row, col, _))
        .map((x: Int) => substitute(a, row, col, x))
        .find(_.isDefined).flatten
      case _ => move(a, row, col)
    }
  }


  def loadSudoku(filename: String): Array[Array[Int]] = {
    val a = Array.ofDim[Int](9, 9)
    var i = 0
    val form = (x: String) => if (x == "-") "0" else x
    val source = scala.io.Source.fromFile(filename)

    for (line <- source.getLines) {
      if (i < 9) {
        a(i) = line.split("").map(form).map(_.toInt)
      }
      i = i + 1
    }
    source.close()
    a
  }
}
