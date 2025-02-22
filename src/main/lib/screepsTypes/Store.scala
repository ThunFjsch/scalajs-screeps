package screepsTypes

import scala.scalajs.js
import js.|
import scala.scalajs.js.Dynamic


@js.native
trait Store[UNLIMITED_STORE <: Boolean] extends js.Object {
/**
  * Returns capacity of this store for the specified resource.
  *
  * For a general purpose store, it returns total capacity if `resource` is undefined.
  * @param resource The type of the resource.
  * @returns Returns capacity number, or `null` in case of an invalid `resource` for this store type.
  */
  def getCapacity(resource: String): Int = js.native
/**
  * Returns the capacity used by the specified resource, or total used capacity for general purpose stores if `resource` is undefined.
  * @param resource The type of the resource.
  * @returns Returns used capacity number, or `null` in case of a not valid `resource` for this store type.
  */
  def getUsedCapacity(resource: String): Int = js.native
/**
  * Returns free capacity for the store.
  *
  * For a limited store, it returns the capacity available for the specified resource if `resource` is defined and valid for this store.
  * @param resource The type of the resource.
  * @returns Returns available capacity number, or `null` in case of an invalid `resource` for this store type.
  */
  def getFreeCapacity(resource: String): Int = js.native
}

