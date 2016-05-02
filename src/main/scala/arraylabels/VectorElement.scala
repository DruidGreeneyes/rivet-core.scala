package main.scala.arraylabels

class VectorElement private (i: Int, v: Double) extends Ordered[VectorElement] {
  //Fields
  val index = i;
  val value = v;
  
  //Constructors
  private def this() = this(0, 0)
  private def this(index: Int) = this(index, 0)
  private def this(value: Double) = this(0, value)
  
  //Core Methods
  override def toString = "%d|%f".format(index, value)
  
  override def compare(other: VectorElement) = index - other.index
  
  def equals(ind: Int) : Boolean = index == ind
  def equals(other: VectorElement) : Boolean = compare(other) == 0
  def ==(ind: Int) : Boolean = this equals ind
  def ==(other: VectorElement) : Boolean = this equals other
  
  def +(v: Double) : VectorElement = new VectorElement(index, value + v)
  def +(other: VectorElement) : VectorElement = 
    if (index == other.index) this + other.value else this
  
  def -(v: Double) : VectorElement = this + -v
  def -(other: VectorElement) : VectorElement = 
    if (index == other.index) this - other.value else this
    
  def *(v: Double) : VectorElement = new VectorElement(index, value * v)
  
  def /(v: Double) : VectorElement = *(1 / v)
  
  def contains(v: Double) = value == v
}

object VectorElement extends VectorElement {
  
  def make(index: Int, value: Double) = new VectorElement(index, value)
  def partial(index: Int) = new VectorElement(index)
  def partial(value: Double) = new VectorElement(value)
  def zero = new VectorElement
  
  def compare(a: VectorElement, b: VectorElement) = a compare b
  
  def fromString(string: String) = {
    val p = string.split("|")
    if (p.length != 2)
      throw new RuntimeException("Proposed VectorElement has too many components: " + string)
    else
      make(p(0).toInt, p(1).toDouble)
  }
}