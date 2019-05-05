import scala.io.Source

object SudokuSolver extends App {
  var a: Array[Array[Int]] = null
  if (args.length == 0) {
    println("Missing input filename")
    System.exit(1)
  }
  a = loadSudoku(args(0))
  a = recurse(a, 0, 0)
  if (a != null) {
    a foreach { row => row foreach print; println }
  }


  def isValidMove(a: => Array[Array[Int]], row: Int, col: Int, candidate: Int): Boolean = {
    var i = 0;
    var j = 0;
    var limi = 0;
    var limj = 0;
    var found = false;
    do {
      if (a(row)(i) == candidate) {
        found = true;
        return !found;
      }
      i = i + 1;
    } while (i < 9)
    i = 0;
    do {
      if (a(i)(col) == candidate) {
        found = true;
        return !found;
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
          found = true;
          return !found;
        }
        j = j + 1;
      } while (j < limj)
      i = i + 1
    } while (i < limi)
    return true;
  }

  def recurse(a: => Array[Array[Int]], row: => Int, col: => Int): Array[Array[Int]] = {
    var i = 1;
    var b: Array[Array[Int]] = null
    var sol: Array[Array[Int]] = null

    var mycol = col
    var myrow = row

    if (a(myrow)(mycol) != 0) {
      mycol = mycol + 1
      if (mycol >= 9) {
        mycol = 0; myrow = myrow + 1
      }
      if (myrow >= 9) {
        return a
      } else {
        b = a.clone()
        return recurse(b, myrow, mycol)
      }
    } else {
      do {
        if (isValidMove(a, row, col, i)) {
          // println("recursing " + row + ", " + col + ": " + i)
          b = a.clone()
          b(row)(col) = i
          sol = recurse(b, row, col)
          if (sol != null) {
            return sol;
          }
        }
        i = i + 1
      } while (i < 10)
      a(myrow)(mycol) = 0
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

