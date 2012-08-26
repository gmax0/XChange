package com.xeiam.xchange.streaming.websocket;

import com.xeiam.xchange.streaming.websocket.exceptions.InvalidDataException;
import com.xeiam.xchange.streaming.websocket.exceptions.InvalidFrameException;
import com.xeiam.xchange.utils.CharsetUtils;

import java.nio.ByteBuffer;

public class CloseFrameBuilder extends DefaultFrameData implements CloseFrame {

  private int code;
  private String reason;

  public CloseFrameBuilder() {
    super(OpCode.CLOSING);
    setFin(true);
  }

  public CloseFrameBuilder(int code) throws InvalidDataException {
    super(OpCode.CLOSING);
    setFin(true);
    setCodeAndMessage(code, "");
  }

  public CloseFrameBuilder(int code, String m) throws InvalidDataException {
    super(OpCode.CLOSING);
    setFin(true);
    setCodeAndMessage(code, m);
  }

  private void setCodeAndMessage(int code, String m) throws InvalidDataException {
    byte[] by = CharsetUtils.toByteArrayUtf8(m);
    ByteBuffer buf = ByteBuffer.allocate(4);
    buf.putInt(code);
    buf.position(2);
    ByteBuffer pay = ByteBuffer.allocate(2 + by.length);
    pay.put(buf);
    pay.put(by);
    setPayload(pay.array());
  }

  private void initCloseCode() throws InvalidFrameException {
    code = CloseFrame.NOCODE;
    byte[] payload = getPayloadData();
    if (payload.length >= 2) {
      ByteBuffer bb = ByteBuffer.allocate(4);
      bb.position(2);
      bb.put(payload, 0, 2);
      bb.position(0);
      code = bb.getInt();
      if (code < 0 || code > Short.MAX_VALUE)
        code = CloseFrame.NOCODE;
      if (code < CloseFrame.NORMAL || code > CloseFrame.EXTENSION || code == NOCODE || code == 1004) {
        throw new InvalidFrameException("bad code " + code);
      }
    }
  }

  @Override
  public int getCloseCode() {
    return code;
  }

  private void initMessage() throws InvalidDataException {
    if (code == CloseFrame.NOCODE) {
      reason = CharsetUtils.toStringUtf8(getPayloadData());
    } else {
      byte[] payload = getPayloadData();
      reason = CharsetUtils.toStringUtf8(payload, 2, payload.length - 2);
    }
  }

  @Override
  public String getMessage() {
    return reason;
  }

  @Override
  public String toString() {
    return super.toString() + "code: " + code;
  }

  @Override
  public void setPayload(byte[] payload) throws InvalidDataException {
    super.setPayload(payload);
    initCloseCode();
    initMessage();
  }

  @Override
  public byte[] getPayloadData() {
    if (code == NOCODE)
      return new byte[0];
    return super.getPayloadData();
  }

}
