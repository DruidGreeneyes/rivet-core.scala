package main.scala.maplabels

import scala.collection.immutable.HashMap
import main.scala.vectorpermutations.Permutations

class RIV (vectorSize: Int, vectorPoints: HashMap[Int, Double]) {
  
  //Fields
  val size = vectorSize
  val points = vectorPoints
  
  //Constructors
  def this (riv: RIV) = this(riv.size, riv.points)
  def this (size: Int) = this(size, new HashMap[Int, Double])
  
  //Methods
  override def toString = points.map(_.toString) mkString("", " ", "|" + size)
    
  def fromString (string: String) = {
    val parts = string.split("\\|")
    new RIV(
        parts(1).toInt, 
        parts(0)
          .split(" ")
          .map(p => (p(0).toInt, p(1).toDouble))
          .foldLeft
            (new HashMap[Int, Double])
            (_ + _))
  }
  
  def merge(other: RIV)(fun: (Double, Double) => Double) = 
    new RIV(size, points.merged(other.points)((p1, p2) => (p1._1, fun.apply(p1._2, p2._2))))
  
  def map(fun: Double => Double) =
    new RIV(size, points.map(p => (p._1, fun.apply(p._2))))
  
  def + (other: RIV) = merge(other)(_ + _)
  
  def - (other: RIV) = merge(other)(_ - _)
  
  def * (scalar: Double) : RIV = map(_ * scalar)
  def * (scalar: Int) : RIV    = *(scalar.asInstanceOf[Double])
  
  def / (scalar: Int)     = *(1 / scalar)
  def / (scalar: Double)  = *(1 / scalar)
  
  def keys = points.keys
  
  def contains(index: Int) = points.contains(index)
  
  def get(index: Int) = points(index)
    
  def magnitude = 
    Math.sqrt(points.values.map(Math.pow(_, 2)) sum)
    
  def normalize = /(magnitude)
  
  private def permutationLoop 
    (points: HashMap[Int, Double], permutation: Array[Int], times: Int)
    : HashMap[Int, Double] =
      if (times == 0) points
      else
        permutationLoop(
            points.map(kv => (permutation(kv._1), kv._2)),
            permutation,
            times - 1)
  
  def permute (permutations: Permutations, times: Int) =
    times match {
      case x if x > 0 => permutationLoop(points, permutations.permutation, times)
      case x if x < 0 => permutationLoop(points, permutations.inverse, -times)
      case _          => this
    }
}