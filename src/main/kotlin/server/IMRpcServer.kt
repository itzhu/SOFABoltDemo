package server

import com.alipay.remoting.ConnectionEventType
import org.slf4j.LoggerFactory
import sofa.bolt.common.BoltServer
import sofa.bolt.common.DISCONNECTEventProcessor
import sofa.bolt.common.CONNECTEventProcessor
import sofa.bolt.common.SimpleServerUserProcessor


class IMRpcServer {
    var logger = LoggerFactory.getLogger(IMRpcServer::class.java)


    private lateinit var server: BoltServer

    private var port = 8999

    private var serverUserProcessor = SimpleServerUserProcessor()
    private var serverConnectProcessor = CONNECTEventProcessor()
    private var serverDisConnectProcessor = DISCONNECTEventProcessor()

    /**
     * 初始化服务器
     * */
    fun startServer() {
        // 1. create a Rpc server with port assigned
        server = BoltServer(port)
        // 2. add processor for connect and close event if you need
        server.addConnectionEventProcessor(ConnectionEventType.CONNECT, serverConnectProcessor)
        server.addConnectionEventProcessor(ConnectionEventType.CLOSE, serverDisConnectProcessor)
        // 3. register user processor for client request
        server.registerUserProcessor(serverUserProcessor)
        // 4. server start
        server.start()
        System.out.println("server start ok!")
    }
}


fun main(vararg args: String) {
    IMRpcServer().startServer()
}