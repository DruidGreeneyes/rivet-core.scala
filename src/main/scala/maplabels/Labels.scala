package main.scala.maplabels

import scala.collection.immutable.HashMap
import scala.util.Random

import main.scala.vectorpermutations.Permutations

object Labels {
  def dotProduct(rivA: RIV, rivB: RIV) =
    rivA
      .keys
      .filter(rivB.contains(_))
      .map(k => rivA.get(k) * rivB.get(k))
      .sum
      
  def similarity(rivA: RIV, rivB: RIV) = {
    val (rA, rB) = (rivA.normalize, rivB.normalize)
    val mag = rA.magnitude * rB.magnitude
    if (mag == 0) 0 else
      dotProduct(rA, rB) / mag
  }
  
  def addLabels (rivs: RIV*) = rivs.reduce(_ + _)
  
  def makeVals (count: Int, seed: Long) =
    new Random(seed)
      .shuffle(Vector.fill(count / 2)(1.0) ++ Vector.fill(count / 2)(-1.0))
  
  def makeIndices (size: Int, count: Int, seed: Long) =
    new Random(seed)
      .shuffle[Int, IndexedSeq](0 until size)
      .take(count)
  
  def makeSeed (seed: String) =
    seed.map(_.toInt).mkString("").toLong
  
  def generateLabel (size: Int, nnz: Int, word: String) = {
    val j = if (nnz % 2 == 0) nnz else nnz + 1
    val seed = makeSeed(word);
    new RIV(
        size,
        makeIndices(size, j, seed)
          .zip(makeVals(j, seed))
          .foldLeft
            (new HashMap[Int, Double])
            (_ + _))
  }
  
  def permuteRIV (riv: RIV, permutations: Permutations, times: Int) = riv.permute(permutations, times)
  
  def generatePermutations (size: Int) = Permutations.generate(size)
}