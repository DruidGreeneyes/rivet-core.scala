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
  
  def add(riv: RIV) : RIV
  
  def subract(riv: RIV) : RIV
  
  def multiply(scalar: Double) : RIV
  
  def divide(scalar: Double) : RIV

  def normalize() : RIV
  
  def permute(permutations: Permutations, times: Int) : RIV

  def magnitude() : Double
}