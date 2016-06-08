package main.scala.arraylabels

import scala.collection.immutable.Vector
import main.scala.vectorpermutations.Permutations

class RIV (vectorSize: Int, vectorPoints: Vector[VectorElement]) {
  
  //Fields
  val size = vectorSize;
  val points = vectorPoints;
  
  //Constructors
  def this (riv: RIV) = this(riv.size, riv.points)
  def this (size: Int) = this(size, Vector.empty)
  
  //Methods
  override def toString = points.map(_.toString) mkString("", " ", ";" + size)
  
  def fromString(string: String) = {
    val parts = string.split(";")
    new RIV(
        parts(1).toInt,
        parts(0)
          .split(" ")
          .map(VectorElement.fromString(_))
          .toVector)
  } 
  
  def get(ind: Int) : VectorElement = 
    if (0 < ind && ind < size)
      points.find(p => p == ind)
        .getOrElse(VectorElement.make(ind, 0))
    else
      throw new IndexOutOfBoundsException("Target index is outside the bounds of this RIV: " + ind)
  def get(point: VectorElement) : VectorElement = get(point.index)
  
  def merge(other: RIV)(conflictFun: (VectorElement, VectorElement) => VectorElement) =
    new RIV(
        size,
        points.intersect(other.points)
          .map(p => conflictFun(get(p), other.get(p)))
          .++(points.diff(other.points))
          .++(other.points.diff(points))
          .sorted)
  
  def map(fun: VectorElement => VectorElement) : RIV =
    new RIV(size, points.map(fun))
  
  def mapValues(fun: Double => Double) : RIV = 
    new RIV(size, points.map(p => VectorElement.make(p.index, fun(p.value))))
  
  def + (other: RIV) = merge(other)(_ + _)
  
  def - (other: RIV) = merge(other)(_ - _)
  
  def * (scalar: Double) = mapValues(_ * scalar)
  
  def / (scalar: Double) = mapValues(_ / scalar)
        
  def contains(elt: VectorElement) = points contains elt
  
  def sum = points.foldLeft(0.0)((i, p) => i + p.value)
  
  def magnitude = Math.sqrt(mapValues(Math.pow(_, 2)).sum)
  
  def normalize = this / magnitude
  
  private def permute(permutation: Array[Int], times: Int) : RIV = 
    0.until(times)
      .foldLeft
        (this)
        ((riv, _) => riv.map(p => VectorElement.make(permutation(p.index), p.value)))
    
  def permute (permutations: Permutations, times: Int) : RIV =
    times match {
      case x if x > 0 => permute(permutations._1, times)
      case x if x < 0 => permute(permutations._2, -times)
      case _          => this
    }
}

object RIV {
  
}