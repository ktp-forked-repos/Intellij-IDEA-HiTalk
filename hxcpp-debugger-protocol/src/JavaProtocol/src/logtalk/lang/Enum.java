package logtalk.lang;

@SuppressWarnings(value={"rawtypes", "unchecked"})
public class Enum
{
	public Enum(int index, logtalk.root.Array<java.lang.Object> params)
	{
		this.index = index;
		this.params = params;
	}
	
	
	public  int index;
	
	public  logtalk.root.Array<java.lang.Object> params;
	
	public   java.lang.String getTag()
	{
		java.lang.Object cl = logtalk.root.Type.getEnum(this);
		return logtalk.lang.Runtime.toString(
                  logtalk.lang.Runtime.callField(logtalk.lang.Runtime.getField(cl, "constructs", true), "__get", new logtalk.root.Array(new java.lang.Object[]{this.index})));
	}
	
	
	@Override public   java.lang.String toString()
	{
		if (( ( this.params == null ) || ( this.params.length == 0 ) )) 
		{
			return this.getTag();
		}
		
		logtalk.root.StringBuf ret = new logtalk.root.StringBuf();
		ret.add(this.getTag());
		ret.add("(");
		boolean first = true;
		{
			int _g = 0;
			logtalk.root.Array<java.lang.Object> _g1 = this.params;
			while (( _g < _g1.length ))
			{
				java.lang.Object p = _g1.__get(_g);
				 ++ _g;
				if (first) 
				{
					first = false;
				}
				 else 
				{
					ret.add(",");
				}
				
				ret.add(p);
			}
			
		}
		
		ret.add(")");
		return ret.toString();
	}
	
	
	@Override public   boolean equals(java.lang.Object obj)
	{
		if (logtalk.lang.Runtime.eq(obj, this))
		{
			return true;
		}
		
		logtalk.lang.Enum obj1 = ((logtalk.lang.Enum) (obj) );
		boolean ret = ((( obj1 != null ) && logtalk.root.Std.is(obj1, logtalk.root.Type.getEnum(this)) ) && (obj1.index == this.index ) );
		if ( ! (ret) ) 
		{
			return false;
		}
		
		if (( obj1.params == this.params )) 
		{
			return true;
		}
		
		int len = 0;
		if (( ( ( obj1.params == null ) || ( this.params == null ) ) || ( (len = this.params.length) != obj1.params.length ) )) 
		{
			return false;
		}
		
		{
			int _g = 0;
			while ((_g < len))
			{
				int i = _g++;
				if ( ! (logtalk.root.Type.enumEq(obj1.params.__get(i), this.params.__get(i))) )
				{
					return false;
				}
				
			}
			
		}
		
		return true;
	}
	
	
	@Override public   int hashCode()
	{
		int h = 19;
		if (( this.params != null )) 
		{
			int _g = 0;
			logtalk.root.Array<java.lang.Object> _g1 = this.params;
			while (( _g < _g1.length ))
			{
				java.lang.Object p = _g1.__get(_g);
				 ++ _g;
				h = ( h * 31 );
				if (( ! (( p == null )) )) 
				{
					h += p.hashCode();
				}
				
			}
			
		}
		
		h += this.index;
		return h;
	}
	
	
}


