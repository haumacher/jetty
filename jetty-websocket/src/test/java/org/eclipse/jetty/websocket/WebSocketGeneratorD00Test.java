package org.eclipse.jetty.websocket;

import static junit.framework.Assert.assertEquals;

import org.eclipse.jetty.io.ByteArrayBuffer;
import org.eclipse.jetty.io.ByteArrayEndPoint;
import org.eclipse.jetty.util.StringUtil;
import org.junit.Before;
import org.junit.Test;

/**
 * @version $Revision$ $Date$
 */
public class WebSocketGeneratorD00Test
{
    private ByteArrayBuffer _out;
    private WebSocketGenerator _generator;

    @Before
    public void setUp() throws Exception
    {
        WebSocketBuffers buffers = new WebSocketBuffers(1024);
        ByteArrayEndPoint endPoint = new ByteArrayEndPoint();
        _generator = new WebSocketGeneratorD00(buffers, endPoint);
        _out = new ByteArrayBuffer(4096);
        endPoint.setOut(_out);
    }

    @Test
    public void testOneString() throws Exception
    {
        byte[] data="Hell\uFF4F W\uFF4Frld".getBytes(StringUtil.__UTF8);
        _generator.addFrame((byte)0x0,(byte)0x04,data,0,data.length);
        _generator.flush();
        assertEquals((byte)0x04,_out.get());
        assertEquals('H',_out.get());
        assertEquals('e',_out.get());
        assertEquals('l',_out.get());
        assertEquals('l',_out.get());
        assertEquals(0xEF,0xff&_out.get());
        assertEquals(0xBD,0xff&_out.get());
        assertEquals(0x8F,0xff&_out.get());
        assertEquals(' ',_out.get());
        assertEquals('W',_out.get());
        assertEquals(0xEF,0xff&_out.get());
        assertEquals(0xBD,0xff&_out.get());
        assertEquals(0x8F,0xff&_out.get());
        assertEquals('r',_out.get());
        assertEquals('l',_out.get());
        assertEquals('d',_out.get());
        assertEquals((byte)0xff,_out.get());
    }

    @Test
    public void testOneBinaryString() throws Exception
    {
        byte[] data="Hell\uFF4F W\uFF4Frld".getBytes(StringUtil.__UTF8);
        _generator.addFrame((byte)0x0,(byte)0x84,data,0,data.length);
        _generator.flush();
        assertEquals((byte)0x84,_out.get());
        assertEquals(15,_out.get());
        assertEquals('H',_out.get());
        assertEquals('e',_out.get());
        assertEquals('l',_out.get());
        assertEquals('l',_out.get());
        assertEquals(0xEF,0xff&_out.get());
        assertEquals(0xBD,0xff&_out.get());
        assertEquals(0x8F,0xff&_out.get());
        assertEquals(' ',_out.get());
        assertEquals('W',_out.get());
        assertEquals(0xEF,0xff&_out.get());
        assertEquals(0xBD,0xff&_out.get());
        assertEquals(0x8F,0xff&_out.get());
        assertEquals('r',_out.get());
        assertEquals('l',_out.get());
        assertEquals('d',_out.get());
    }

}
