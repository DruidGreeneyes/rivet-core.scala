package main.scala.riv

trait RIV {
  def points() : Vector[VectorElement]
  def size() : Int
  def count() : Int
  
  def toString() : String
  
  def keys() : Array[Int]
  def vals() : Array[Double]
  
  def contains(index: Int) : Boolean
  def equals(riv: RIV) : Boolean
  
  def get(index: Int) : Double
  
  def copy() : RIV
  def mapKeys(fun: Function1[Int, Int]) : RIV
  def mapVals(fun: Function1[Double, Double]) : RIV
  def add(riv: RIV) : RIV
  def subract(riv: RIV) : RIV
  def multiply(scalar: Double) : RIV
  def divide(scalar: Double) : RIV
  
  def normalize() : RIV
  
  def permute(permutations: Permutations, times: Int) : RIV
  
  def magnitude() = vals().map(Math.pow(_, 2)).reduce(_ + _)
}

object RIV {
  def dotProduct(riva: RIV, rivb: RIV) : Double =
    riva.keys
      .intersect(rivb.keys)
      .map(k => riva.get(k) * rivb.get(k))
      .reduce(_ + _)
  
  def similarity(riva: RIV, rivb: RIV) = {
    val mag = riva.magnitude * rivb.magnitude
    if (mag == 0)
      0
    else
      dotProduct(riva, rivb) / mag
  }
  
  
}