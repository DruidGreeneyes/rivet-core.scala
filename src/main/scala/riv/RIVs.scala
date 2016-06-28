package main.scala.riv

object RIVs {
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