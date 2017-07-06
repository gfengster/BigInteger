package net.gf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map.Entry;

public class BigData implements Entry<Integer, BigData> {
	private static final int BASE = 10;
		
	public static BigData build(final long input) {
		long value = input;
		final BigData ret = new BigData(0);
		BigData data = null;
		int bit = 0;
		
		while (value != 0) {
			bit = (int)(value % BASE);
			value = (long)(value/BASE);
			
			if (data == null) {
				ret.value = bit;
				data = ret;
			} else {
				final BigData next = new BigData(bit);
				data.next = next;
				
				data = next;
			}
		}
		
		return ret ;
	}
	
	public static BigData build(final int input) {
		return build((long)input);
	}
	
	private int value = 0;
	private BigData next = null;
	
	private BigData(){
	}
	
	private BigData(int bit) {
		this.value = bit;
	}
	
	private BigData(int bit, BigData next) {
		this.value = bit;
		this.next = next;
	}
	
	public BigData add (final BigData other) {
		int reminder = 0;
		
		int v = value + other.value;
		if (v >= BASE) {
			v = v - BASE;
			reminder = 1;
		}
				
		final BigData ret = new BigData(v);
		BigData data = ret;
		
		BigData myNext = next;
		BigData oNext = other.next;
		
		do {
			v = reminder;
			
			if (myNext != null) {
				v += myNext.value;
				myNext = myNext.next;
			}
			
			if (oNext != null) {
				v += oNext.value;
				oNext = oNext.next;
			}

			if (v >= BASE) {
				v = v - BASE;
				reminder = 1;
			} else 
				reminder = 0;

			data = add(data, v);

		} while (myNext != null || oNext != null);
	
		if (reminder == 1) {
			data = add(data, reminder);
		}
		
		return ret;
	}
	
	public BigData minus(BigData other) {
		int borrow = 0;
		
		int v = value - other.value;
		if (v < 0) {
			v = v + BASE;
			borrow = 1;
		}
				
		final BigData ret = new BigData(v);
		BigData data = ret;
		
		BigData myNext = next;
		BigData oNext = other.next;
		
		while (myNext != null && oNext != null) {
			v = borrow;
			
			v = myNext.value - oNext.value - borrow;
			myNext = myNext.next;
			oNext = oNext.next;
			
			if (v < 0) {
				v = v + BASE;
				borrow = 1;
			} else 
				borrow = 0;
			
			data = add(data, v);
		}
		
		while (myNext != null) {
			v = myNext.value - borrow;
			myNext = myNext.next;
			
			if (v < 0) {
				v = v + BASE;
				borrow = 1;
			} else 
				borrow = 0;
			
			data = add(data, v);
		}
		
		if (borrow > 0)
			data.value -= BASE;
		
		return ret;
	}
	
	private BigData add(BigData data, int v) {
		final BigData nNext = new BigData(v);
		
		data.next = nNext;
			
		return nNext;
	}
	
	@Override
	public String toString(){
		StringBuilder ret = new StringBuilder(String.valueOf(value));
		BigData n = next;
		
		while(n != null) {
			ret.append(n.value);
			n = n.next;
		}
		
		if (ret.charAt(0) == '-') {
			ret.deleteCharAt(0);
			ret.append('-');
		}
		
		return ret.reverse().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((next == null) ? 0 : next.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BigData other = (BigData) obj;
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	public BigData reverse() {
		BigData ret = new BigData(value);
		BigData n = next;
		
		while (n != null) {
			final BigData tmp = new BigData(n.value, n.next);
			
			n = n.next;
		
			tmp.next = ret;
			
			ret = tmp;
		}
			
		return ret;
	}
	
	public void write(OutputStream out) throws IOException {
		BigData reverse = reverse();
		
		out.write(reverse.value + 48);
		
		while (reverse.next != null) {
			reverse = reverse.next;
			
			out.write(reverse.value + 48);
		}
	}

	public Integer getKey() {
		return value;
	}

	public BigData getValue() {
		return next;
	}

	public BigData setValue(BigData value) {
		return next = value;
	}
}
