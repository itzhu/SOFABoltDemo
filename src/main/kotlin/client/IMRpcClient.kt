package client

import com.alipay.remoting.ConnectionEventType
import com.alipay.remoting.rpc.RpcClient
import sofa.bolt.common.DISCONNECTEventProcessor
import sofa.bolt.common.CONNECTEventProcessor
import sofa.bolt.common.RequestBody
import sofa.bolt.common.SimpleClientUserProcessor
import com.alipay.remoting.exception.RemotingException
import org.slf4j.LoggerFactory

const val addr = "localhost:8999"

class IMRpcClient {
    var logger = LoggerFactory.getLogger(IMRpcClient::class.java)

    private lateinit var client: RpcClient

    private var clientUserProcessor = SimpleClientUserProcessor()
    private var clientConnectProcessor = CONNECTEventProcessor()
    private var clientDisConnectProcessor = DISCONNECTEventProcessor()

    fun initClient(): RpcClient {
        // 1. create a rpc client
        client = RpcClient()
        // 2. add processor for connect and close event if you need
        client.addConnectionEventProcessor(ConnectionEventType.CONNECT, clientConnectProcessor)
        client.addConnectionEventProcessor(ConnectionEventType.CLOSE, clientDisConnectProcessor)
        // 3. do init
        client.init()
        return client
    }
}

fun main(vararg args: String) {

    var client = IMRpcClient().initClient()
    for (i in 5..10) {
        var requestBody = RequestBody(1, "this is client msg")
        try {
            var res = client.invokeSync(addr, requestBody, 3000)
            println("$i invoke sync result = [$res]")
        } catch (e: RemotingException) {
            val errMsg = "RemotingException caught in oneway!"
            println(errMsg)
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    client.shutdown()
}