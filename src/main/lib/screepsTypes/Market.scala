package screepsTypes

import scala.scalajs.js
import scala.scalajs.js.annotation._

/**
 * A global object representing the in-game market.
 *
 * You can use this object to track resource transactions to/from your terminals, and your buy/sell orders.
 * The object is accessible via the singleton {@link Game.market} property.
 *
 * Learn more about the market system from [this article](https://docs.screeps.com/market.html).
 */
@js.native
trait Market extends js.Object {
    /** Your current credits balance. */
    val credits: Int = js.native
    /** An array of the last 100 incoming transactions to your terminals */
    val incomingTransactions: Array[Transaction] = js.native
    /** An array of the last 100 outgoing transactions from your terminals */
    val outgoingTransactions: Array[Transaction] = js.native
    /** An object with your active and inactive buy/sell orders on the market. */
    val orders: OrderList = js.native
    /**
     * Estimate the energy transaction cost of {@link StructureTerminal.send} and {@link Market.deal} methods.
     *
     * The formula:
     *
     * ```
     * Math.ceil( amount * (Math.log(0.1*linearDistanceBetweenRooms + 0.9) + 0.1) )
     * ```
     *
     * @param amount Amount of resources to be sent.
     * @param roomName1 The name of the first room.
     * @param roomName2 The name of the second room.
     * @returns The amount of energy required to perform the transaction.
     */
    def calcTransactionCost(amount: Int, roomNameOne: String, roomNameTwo: String): Int = js.native
    /**
     * Cancel a previously created order.
     *
     * The 5% fee is not returned.
     * @param orderId The order ID as provided in Game.market.orders
     * @returns One of the following codes:
     * - OK: The operation has been scheduled successfully.
     * - ERR_INVALID_ARGS: The order ID is not valid.
     */
    def cancelOrder(orderId: String): Int = js.native
    /**
     * Change the price of an existing order.
     *
     * If `newPrice` is greater than old price, you will be charged `(newPrice-oldPrice)*remainingAmount*0.05` credits.
     * @param orderId The order ID as provided in Game.market.orders
     * @param newPrice The new order price.
     * @returns One of the following codes:
     * - OK: The operation has been scheduled successfully.
     * - ERR_NOT_OWNER: You are not the owner of the room's terminal or there is no terminal.
     * - ERR_NOT_ENOUGH_RESOURCES: You don't have enough credits to pay a fee.
     * - ERR_INVALID_ARGS: The arguments provided are invalid.
     */
    def changeOrderPrice(orderId: String, newPrice: Float): Int = js.native
    /**
     * Create a market order in your terminal.
     *
     * You will be charged `price*amount*0.05` credits when the order is placed.
     *
     * The maximum orders count is 300 per player. You can create an order at any time with any amount,
     * it will be automatically activated and deactivated depending on the resource/credits availability.
     *
     * An order expires in 30 days after its creation, and the remaining market fee is returned.
     *
     * @param params A object describing the order.
     * @returns One of the following codes:
     * - OK: The operation has been scheduled successfully.
     * - ERR_NOT_OWNER: You are not the owner of the room's terminal or there is no terminal.
     * - ERR_NOT_ENOUGH_RESOURCES: You don't have enough credits to pay a fee.
     * - ERR_FULL: You cannot create more than 50 orders.
     * - ERR_INVALID_ARGS: The arguments provided are invalid.
     */
    def createOrder(params: CreateOrderParam): Int = js.native
    /**
     * Execute a trade deal from your Terminal to another player's Terminal using the specified buy/sell order.
     *
     * Your Terminal will be charged energy units of transfer cost regardless of the order resource type.
     * You can use {@link Game.market.calcTransactionCost} method to estimate it.
     *
     * When multiple players try to execute the same deal, the one with the shortest distance takes precedence.
     *
     * You cannot execute more than 10 deals during one tick.
     *
     * @param orderId The order ID as provided in Game.market.orders
     * @param amount The amount of resources to transfer.
     * @param yourRoomName The name of your room which has to contain an active Terminal with enough amount of energy.
     * This argument is not used when the order resource type is one of account-bound resources (@see {@link InterShardResourceConstant}).
     *
     * @returns One of the following error codes:
     * - OK: The operation has been scheduled successfully.
     * - ERR_NOT_OWNER: You don't have a terminal in the target room.
     * - ERR_NOT_ENOUGH_RESOURCES: You don't have enough credits or resource units.
     * - ERR_FULL: You cannot execute more than 10 deals during one tick.
     * - ERR_INVALID_ARGS: The arguments provided are invalid.
     * - ERR_TIRED: The target terminal is still cooling down.
     */
    def deal(orderId: String, amount: Int, yourRoomName: js.UndefOr[String]): Int = js.native
    /**
     * Add more capacity to an existing order.
     *
     * It will affect `remainingAmount` and `totalAmount` properties. You will be charged `price*addAmount*0.05` credits.
     * Extending the order doesn't update its expiration time.
     *
     * @param orderId The order ID as provided in Game.market.orders
     * @param addAmount How much capacity to add. Cannot be a negative value.
     * @returns One of the following error codes:
     * - OK: The operation has been scheduled successfully.
     * - ERR_NOT_ENOUGH_RESOURCES: You don't have enough credits to pay a fee.
     * - ERR_INVALID_ARGS:  The arguments provided are invalid.
     */
    def extendOrder(orderId: String, addAmount: Int): Int = js.native
    /**
     * Get other players' orders currently active on the market.
     * @param filter (optional) An object or function that will filter the resulting list using the lodash.filter method.
     * @returns An array of objects containing order information.
     */
    def getAllOrders(filter: js.UndefOr[Either[OrderFilter, js.Function1[Order, Boolean]]] = js.undefined): js.Array[Order] = js.native
    /**
     * Get daily price history of the specified resource on the market for the last 14 days.
     * @param resource One of the {@link MarketResourceConstant RESOURCE_*} constants. If undefined, returns history data for all resources. Optional
     * @returns An array of objects with resource info.
     */
    def getHistory(resource: js.UndefOr[ResourceType.type]): PriceHistory = js.native
    /**
     * Retrieve info for specific market order.
     * @param orderId The order ID.
     * @returns An object with the order info. See {@link Order}.
     */
    def getOrderById(id: String): Either[Order, Null] = js.native
}

@js.native
trait TransactionOrder extends js.Object {
    val id: String = js.native
    @JSName("type")
    val typ: String = js.native
    val price: Float = js.native
}

@js.native
trait Transaction extends js.Object {
    val transactionId: String = js.native
    val time: Int = js.native
    val sender: js.UndefOr[Sender] = js.native
    val recipient: js.UndefOr[Sender] = js.native
    val resourceType: Either[ResourceType.type, InterShardResourceConstant.type] = js.native
    val amount: Int = js.native
    val from: String = js.native
    val to: String = js.native
    val description: String = js.native
    val order: js.UndefOr[TransactionOrder] 
}

@js.native
trait Sender extends js.Object {
    val username: String = js.native
}

@js.native
trait OrderList extends js.Object {
    @JSBracketAccess
    def apply(order: String): Order
}

@js.native
trait CreateOrderParam extends js.Object {
    @JSName("type")
    val typ: String = js.native
    val resourceType: ResourceType.type = js.native
    val price: Float = js.native
    val totalAmount: Int = js.native
    val roomName: js.UndefOr[String] = js.native   
}

@js.native
trait Order extends js.Object {
    /** The unique order ID. */
    val id: String = js.native
    /**
     * The order creation time in milliseconds since UNIX epoch time.
     *
     * This property is absent for old orders.
     */
    val created: Int = js.native
    /** Whether the order is active or not.
     *
     * Only exists for your own orders. */
    val active: js.UndefOr[Boolean] = js.native
    
    /** The order type. */
    // TODO: type: ORDER_BUY | ORDER_SELL; should this be represented with Enumaration of these strings or what? 
    @JSName("type")
    val typ: String = js.native
    /** The type of resource requested by the order. See {@link ResourceType}. */
    val resourceType: String = js.native
    /** The room where this order is placed. */
    val roomName: js.UndefOr[String] = js.native
    /** Currently available quantity of resource to trade. */
    val amount: Int = js.native
    /** Remaining quantity of resources to trade. */
    val remainingAmount: Int = js.native
    /** Total quantity of resources traded */
    val totalAmount: js.UndefOr[Int] = js.native
    /** The current price per unit. */
    val price: Float = js.native
}

@js.native
trait OrderFilter extends js.Object {
    val id: js.UndefOr[String] = js.native
    val created: js.UndefOr[Int] = js.native
    @JSName("type")   
    val typ: js.UndefOr[String] = js.native
    val resourceType: js.UndefOr[ResourceType.type] = js.native
    val roomName: js.UndefOr[String] = js.native
    val amount: js.UndefOr[Int] = js.native
    val remainingAmount: js.UndefOr[Int] = js.native
    val price: js.UndefOr[Float] = js.native
}

// TODO: Do these values need float?
@js.native
trait PriceHistory extends js.Object {
    val resourceType: ResourceType.type = js.native
    val date: String = js.native
    val transactions: Int = js.native
    val valume: Int = js.native
    val avgPrice: Int = js.native
    val stddevPrice: Int = js.native
}