package logtalk.root;

@SuppressWarnings(value={"rawtypes", "unchecked"})
public  class List<T> extends logtalk.lang.HxObject
{
	public    List(logtalk.lang.EmptyObject empty)
	{
		{
		}
		
	}
	
	
	public    List()
	{
		logtalk.root.List.__hx_ctor__List(this);
	}
	
	
	public static  <T_c> void __hx_ctor__List(logtalk.root.List<T_c> __temp_me17)
	{
		__temp_me17.length = 0;
	}
	
	
	public static   java.lang.Object __hx_createEmpty()
	{
		return new logtalk.root.List<java.lang.Object>(((logtalk.lang.EmptyObject) (logtalk.lang.EmptyObject.EMPTY) ));
	}
	
	
	public static   java.lang.Object __hx_create(logtalk.root.Array arr)
	{
		return new logtalk.root.List<java.lang.Object>();
	}
	
	
	public  logtalk.root.Array h;
	
	public  logtalk.root.Array q;
	
	public  int length;
	
	public   void add(T item)
	{
		logtalk.root.Array x = new logtalk.root.Array(new java.lang.Object[]{item});
		if (( this.h == null )) 
		{
			this.h = x;
		}
		 else 
		{
			this.q.__set(1, x);
		}
		
		this.q = x;
		this.length++;
	}
	
	
	public   java.lang.Object iterator()
	{
		logtalk.root.Array<logtalk.root.Array> h = new logtalk.root.Array<logtalk.root.Array>(new logtalk.root.Array[]{this.h});
		java.lang.Object __temp_stmt71 = null;
		{
			logtalk.lang.Function __temp_odecl69 = new logtalk.root.List_iterator_165__Fun(((logtalk.root.Array<logtalk.root.Array>) (h) ));
			logtalk.lang.Function __temp_odecl70 = new logtalk.root.List_iterator_168__Fun(((logtalk.root.Array<logtalk.root.Array>) (h) ));
			__temp_stmt71 = new logtalk.lang.DynamicObject(new logtalk.root.Array<java.lang.String>(new java.lang.String[]{"hasNext", "next"}), new logtalk.root.Array<java.lang.Object>(new java.lang.Object[]{__temp_odecl69, __temp_odecl70}), new logtalk.root.Array<java.lang.String>(new java.lang.String[]{}), new logtalk.root.Array<java.lang.Object>(new java.lang.Object[]{}));
		}
		
		return ((java.lang.Object) (__temp_stmt71) );
	}
	
	
	@Override public   double __hx_setField_f(java.lang.String field, double value, boolean handleProperties)
	{
		{
			boolean __temp_executeDef72 = true;
			switch (field.hashCode())
			{
				case -1106363674:
				{
					if (field.equals("length")) 
					{
						__temp_executeDef72 = false;
						this.length = ((int) (value) );
						return value;
					}
					
					break;
				}
				
				
			}
			
			if (__temp_executeDef72) 
			{
				return super.__hx_setField_f(field, value, handleProperties);
			}
			 else 
			{
				throw null;
			}
			
		}
		
	}
	
	
	@Override public   java.lang.Object __hx_setField(java.lang.String field, java.lang.Object value, boolean handleProperties)
	{
		{
			boolean __temp_executeDef73 = true;
			switch (field.hashCode())
			{
				case -1106363674:
				{
					if (field.equals("length")) 
					{
						__temp_executeDef73 = false;
						this.length = ((int) (logtalk.lang.Runtime.toInt(value)) );
						return value;
					}
					
					break;
				}
				
				
				case 104:
				{
					if (field.equals("h")) 
					{
						__temp_executeDef73 = false;
						this.h = ((logtalk.root.Array) (value) );
						return value;
					}
					
					break;
				}
				
				
				case 113:
				{
					if (field.equals("q")) 
					{
						__temp_executeDef73 = false;
						this.q = ((logtalk.root.Array) (value) );
						return value;
					}
					
					break;
				}
				
				
			}
			
			if (__temp_executeDef73) 
			{
				return super.__hx_setField(field, value, handleProperties);
			}
			 else 
			{
				throw null;
			}
			
		}
		
	}
	
	
	@Override public   java.lang.Object __hx_getField(java.lang.String field, boolean throwErrors, boolean isCheck, boolean handleProperties)
	{
		{
			boolean __temp_executeDef74 = true;
			switch (field.hashCode())
			{
				case 1182533742:
				{
					if (field.equals("iterator")) 
					{
						__temp_executeDef74 = false;
						return ((logtalk.lang.Function) (new logtalk.lang.Closure(((java.lang.Object) (this) ), logtalk.lang.Runtime.toString("iterator"))) );
					}
					
					break;
				}
				
				
				case 104:
				{
					if (field.equals("h")) 
					{
						__temp_executeDef74 = false;
						return this.h;
					}
					
					break;
				}
				
				
				case 96417:
				{
					if (field.equals("add")) 
					{
						__temp_executeDef74 = false;
						return ((logtalk.lang.Function) (new logtalk.lang.Closure(((java.lang.Object) (this) ), logtalk.lang.Runtime.toString("add"))) );
					}
					
					break;
				}
				
				
				case 113:
				{
					if (field.equals("q")) 
					{
						__temp_executeDef74 = false;
						return this.q;
					}
					
					break;
				}
				
				
				case -1106363674:
				{
					if (field.equals("length")) 
					{
						__temp_executeDef74 = false;
						return this.length;
					}
					
					break;
				}
				
				
			}
			
			if (__temp_executeDef74) 
			{
				return super.__hx_getField(field, throwErrors, isCheck, handleProperties);
			}
			 else 
			{
				throw null;
			}
			
		}
		
	}
	
	
	@Override public   double __hx_getField_f(java.lang.String field, boolean throwErrors, boolean handleProperties)
	{
		{
			boolean __temp_executeDef75 = true;
			switch (field.hashCode())
			{
				case -1106363674:
				{
					if (field.equals("length")) 
					{
						__temp_executeDef75 = false;
						return ((double) (this.length) );
					}
					
					break;
				}
				
				
			}
			
			if (__temp_executeDef75) 
			{
				return super.__hx_getField_f(field, throwErrors, handleProperties);
			}
			 else 
			{
				throw null;
			}
			
		}
		
	}
	
	
	@Override public   java.lang.Object __hx_invokeField(java.lang.String field, logtalk.root.Array dynargs)
	{
		{
			boolean __temp_executeDef76 = true;
			switch (field.hashCode())
			{
				case 1182533742:
				{
					if (field.equals("iterator")) 
					{
						__temp_executeDef76 = false;
						return this.iterator();
					}
					
					break;
				}
				
				
				case 96417:
				{
					if (field.equals("add")) 
					{
						__temp_executeDef76 = false;
						this.add(((T) (dynargs.__get(0)) ));
					}
					
					break;
				}
				
				
			}
			
			if (__temp_executeDef76) 
			{
				return super.__hx_invokeField(field, dynargs);
			}
			
		}
		
		return null;
	}
	
	
	@Override public   void __hx_getFields(logtalk.root.Array<java.lang.String> baseArr)
	{
		baseArr.push("length");
		baseArr.push("q");
		baseArr.push("h");
		{
			super.__hx_getFields(baseArr);
		}
		
	}
	
	
}


