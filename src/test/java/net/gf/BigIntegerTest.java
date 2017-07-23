package net.gf;

import static org.junit.Assert.assertEquals;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class BigDataTest {

	//@Test
	public void build1Test() {
		long value = 65123;

		BigData data = BigData.build(value);

		assertEquals(value, Long.parseLong(data.toString()));

		value = Long.MAX_VALUE;
		data = BigData.build(value);

		assertEquals(value, Long.parseLong(data.toString()));
	}

	//@Test
	public void addTest() {
		long value1 = 19528l;
		long value2 = 23222923l;

		BigData data1 = BigData.build(value1);
		BigData data2 = BigData.build(value2);

		BigData data = data1.add(data2);

		assertEquals(value1 + value2, Long.parseLong(data.toString()));

		value1 = 99528l;
		value2 = 99899l;

		data1 = BigData.build(value1);
		data2 = BigData.build(value2);

		data = data1.add(data2);

		assertEquals(value1 + value2, Long.parseLong(data.toString()));

		value1 = 12l;
		value2 = 23222923l;

		data1 = BigData.build(value1);
		data2 = BigData.build(value2);

		data = data1.add(data2);

		assertEquals(value1 + value2, Long.parseLong(data.toString()));
	}
	
	//@Test
	public void build2Test() {
		long value = Long.MAX_VALUE;

		BigData data = BigData.build(value);
		
		System.out.println(data);
		
		data = data.add(BigData.build(99));
		
		System.out.println(data);
		assertEquals("9223372036854775906", data.toString());
	}
	
	//@Test
	public void build3Test() {
		long value = Long.MAX_VALUE;

		BigData data = BigData.build(value);
				
		data = data.add(BigData.build(value));
		
		System.out.println(data);
		assertEquals("18446744073709551614", data.toString());
	}
	
	//@Test
	public void reverseTest() throws IOException {
		long value = Long.MAX_VALUE;

		BigData data = BigData.build(value);
		System.out.println(data);
		System.out.println(data.reverse());
		System.out.println(data);
		
		DataOutputStream file  = new DataOutputStream(new FileOutputStream("./tmp"));
		
		data.write(file);
		file.flush();
		
		file.close();
	}
	
	@Test
	public void minusTest() {
		long value1 = 6l;
		long value2 = 9l;

		BigData data1 = BigData.build(value1);
		BigData data2 = BigData.build(value2);

		BigData data = data1.minus(data2);

		assertEquals(value1 - value2, Long.parseLong(data.toString()));
		
		
		value1 = 126l;
		value2 = 89l;

		data1 = BigData.build(value1);
		data2 = BigData.build(value2);

		data = data1.minus(data2);

		assertEquals(value1 - value2, Long.parseLong(data.toString()));
	}
}
