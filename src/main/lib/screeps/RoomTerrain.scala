package screepsTypes

import scala.scalajs.js

/**
 * An object which provides fast access to room terrain data.
 *
 * These objects can be constructed for any room in the world even if you have no access to it.
 *
 * Technically every Room.Terrain object is a very lightweight adapter to underlying static terrain buffers with corresponding minimal accessors.
 */
@js.native
trait RoomTerrain extends js.Object{
    /**
     * Get terrain type at the specified room position.
     *
     * This method works for any room in the world even if you have no access to it.
     * @param x X position in the room.
     * @param y Y position in the room.
     * @return A bitmask of {@link TERRAIN_MASK_WALL}, {@link TERRAIN_MASK_SWAMP}.
     */
    def get(x: Int, y: Int): Int = js.native
    /**
     * Get a copy of the underlying static terrain buffer.
     * @param destinationArray (optional) A typed array view in which terrain will be copied to.
     * @throws {RangeError} if `destinationArray` is provided, it must have a length of at least 2500 (`50*50`).
     * @return Copy of underlying room terrain as a new typed array of size 2500.
     */
    def getRawBuffer(): js.Array[Short] = js.native
}