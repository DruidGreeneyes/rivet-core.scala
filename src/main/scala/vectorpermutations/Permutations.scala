package main.scala.vectorpermutations

import scala.util.Random

class Permutations private (permute: Array[Int], invert: Array[Int]) {
  val permutation = permute
  val inverse = invert
}

object Permutations { 
  def generate(size: Int) = {
    val range = 0 until size
    val permutation = new Random(0).shuffle[Int, IndexedSeq](range).toArray
    val inverse = range.map(permutation.indexOf(_)).toArray
    new Permutations(permutation, inverse)
  }
}
