// automatically generated, do not modify

package my.test;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

@SuppressWarnings("unused")
public final class Person extends Table {
  public static Person getRootAsPerson(ByteBuffer _bb) { return getRootAsPerson(_bb, new Person()); }
  public static Person getRootAsPerson(ByteBuffer _bb, Person obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public Person __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public String name() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public byte gender() { int o = __offset(6); return o != 0 ? bb.get(o + bb_pos) : 0; }
  public boolean mutateGender(byte gender) { int o = __offset(6); if (o != 0) { bb.put(o + bb_pos, gender); return true; } else { return false; } }
  public int age() { int o = __offset(8); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public boolean mutateAge(int age) { int o = __offset(8); if (o != 0) { bb.putInt(o + bb_pos, age); return true; } else { return false; } }

  public static int createPerson(FlatBufferBuilder builder,
      int name,
      byte gender,
      int age) {
    builder.startObject(3);
    Person.addAge(builder, age);
    Person.addName(builder, name);
    Person.addGender(builder, gender);
    return Person.endPerson(builder);
  }

  public static void startPerson(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(0, nameOffset, 0); }
  public static void addGender(FlatBufferBuilder builder, byte gender) { builder.addByte(1, gender, 0); }
  public static void addAge(FlatBufferBuilder builder, int age) { builder.addInt(2, age, 0); }
  public static int endPerson(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

