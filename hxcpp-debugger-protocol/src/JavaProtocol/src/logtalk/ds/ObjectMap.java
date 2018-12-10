package logtalk.ds;

@SuppressWarnings(value={"rawtypes", "unchecked"})
public  class ObjectMap<K, V> extends logtalk.lang.HxObject implements logtalk.root.IMap<K, V>
{
	public    ObjectMap(logtalk.lang.EmptyObject empty)
	{
		{
		}
		
	}
	
	
	public    ObjectMap()
	{
		logtalk.ds.ObjectMap.__hx_ctor_haxe_ds_ObjectMap(this);
	}
	
	
	public static  <K_c, V_c> void __hx_ctor_haxe_ds_ObjectMap(logtalk.ds.ObjectMap<K_c, V_c> __temp_me27)
	{
		__temp_me27.cachedIndex = -1;
	}
	
	
	public static   java.lang.Object __hx_createEmpty()
	{
		return new logtalk.ds.ObjectMap<java.lang.Object, java.lang.Object>(((logtalk.lang.EmptyObject) (logtalk.lang.EmptyObject.EMPTY) ));
	}
	
	
	public static   java.lang.Object __hx_create(logtalk.root.Array arr)
	{
		return new logtalk.ds.ObjectMap<java.lang.Object, java.lang.Object>();
	}
	
	
	public  int[] hashes;
	
	public  K[] _keys;
	
	public  V[] vals;
	
	public  int nBuckets;
	
	public  int size;
	
	public  int nOccupied;
	
	public  int upperBound;
	
	public  K cachedKey;
	
	public  int cachedIndex;
	
	public   void set(K key, V value)
	{
		int x = 0;
		int k = 0;
		if (( this.nOccupied >= this.upperBound )) 
		{
			if (( this.nBuckets > ( this.size << 1 ) )) 
			{
				this.resize(( this.nBuckets - 1 ));
			}
			 else 
			{
				this.resize(( this.nBuckets + 2 ));
			}
			
		}
		
		int[] hashes = this.hashes;
		K[] keys = this._keys;
		int[] hashes1 = hashes;
		{
			int mask = 0;
			if (( this.nBuckets == 0 )) 
			{
				mask = 0;
			}
			 else 
			{
				mask = ( this.nBuckets - 1 );
			}
			
			int site = x = this.nBuckets;
			{
				int k1 = key.hashCode();
				k1 = ( ( k1 + 2127912214 ) + (( k1 << 12 )) );
				k1 = ( ( k1 ^ -949894596 ) ^ ( k1 >> 19 ) );
				k1 = ( ( k1 + 374761393 ) + (( k1 << 5 )) );
				k1 = ( ( k1 + -744332180 ) ^ ( k1 << 9 ) );
				k1 = ( ( k1 + -42973499 ) + (( k1 << 3 )) );
				k1 = ( ( k1 ^ -1252372727 ) ^ ( k1 >> 16 ) );
				int ret = k1;
				if (( (( ret & -2 )) == 0 )) 
				{
					if (( ret == 0 )) 
					{
						ret = 2;
					}
					 else 
					{
						ret = -1;
					}
					
				}
				
				k = ret;
			}
			
			int i = ( k & mask );
			int nProbes = 0;
			if (( (( hashes1[i] & -2 )) == 0 )) 
			{
				x = i;
			}
			 else 
			{
				int last = i;
				int flag = 0;
				do 
				{
					boolean __temp_stmt144 = false;
					{
						int v = flag = hashes1[i];
						__temp_stmt144 = ( (( v & -2 )) == 0 );
					}
					
					boolean __temp_boolv145 = false;
					if ( ! (__temp_stmt144) ) 
					{
						__temp_boolv145 = ( ( flag == k ) && keys[i].equals(key) );
					}
					
					boolean __temp_stmt143 = ( __temp_stmt144 || __temp_boolv145 );
					if ( ! ((__temp_stmt143)) ) 
					{
						i = ( ( i +  ++ nProbes ) & mask );
					}
					 else 
					{
						break;
					}
					
				}
				while (true);
				x = i;
			}
			
		}
		
		int flag = hashes1[x];
		if (( flag == 0 )) 
		{
			keys[x] = key;
			this.vals[x] = value;
			hashes1[x] = k;
			this.size++;
			this.nOccupied++;
		}
		 else 
		{
			if (( flag == 1 )) 
			{
				keys[x] = key;
				this.vals[x] = value;
				hashes1[x] = k;
				this.size++;
			}
			 else 
			{
				this.vals[x] = value;
			}
			
		}
		
		this.cachedIndex = x;
		this.cachedKey = key;
	}
	
	
	public   int lookup(K key)
	{
		if (( this.nBuckets != 0 )) 
		{
			int[] hashes = this.hashes;
			K[] keys = this._keys;
			int mask = ( this.nBuckets - 1 );
			int hash = 0;
			{
				int k = key.hashCode();
				k = ( ( k + 2127912214 ) + (( k << 12 )) );
				k = ( ( k ^ -949894596 ) ^ ( k >> 19 ) );
				k = ( ( k + 374761393 ) + (( k << 5 )) );
				k = ( ( k + -744332180 ) ^ ( k << 9 ) );
				k = ( ( k + -42973499 ) + (( k << 3 )) );
				k = ( ( k ^ -1252372727 ) ^ ( k >> 16 ) );
				int ret = k;
				if (( (( ret & -2 )) == 0 )) 
				{
					if (( ret == 0 )) 
					{
						ret = 2;
					}
					 else 
					{
						ret = -1;
					}
					
				}
				
				hash = ret;
			}
			
			int k = hash;
			int nProbes = 0;
			int i = ( k & mask );
			int last = i;
			int flag = 0;
			do 
			{
				boolean __temp_stmt149 = false;
				{
					int v = flag = hashes[i];
					__temp_stmt149 = ( v == 0 );
				}
				
				boolean __temp_boolv148 =  ! (__temp_stmt149) ;
				boolean __temp_boolv147 = false;
				if (__temp_boolv148) 
				{
					__temp_boolv147 = (( ( ( flag == 1 ) || ( flag != k ) ) ||  ! (keys[i].equals(key))  ));
				}
				
				boolean __temp_stmt146 = ( __temp_boolv148 && __temp_boolv147 );
				if (__temp_stmt146) 
				{
					i = ( ( i +  ++ nProbes ) & mask );
				}
				 else 
				{
					break;
				}
				
			}
			while (true);
			if (( (( flag & -2 )) == 0 )) 
			{
				return -1;
			}
			 else 
			{
				return i;
			}
			
		}
		
		return -1;
	}
	
	
	public   void resize(int newNBuckets)
	{
		int[] newHash = null;
		int j = 1;
		{
			{
				int x = newNBuckets;
				 -- x;
				x |= ( x >>> 1 );
				x |= ( x >>> 2 );
				x |= ( x >>> 4 );
				x |= ( x >>> 8 );
				x |= ( x >>> 16 );
				newNBuckets =  ++ x;
			}
			
			if (( newNBuckets < 4 )) 
			{
				newNBuckets = 4;
			}
			
			if (( this.size >= ( ( newNBuckets * 0.77 ) + 0.5 ) )) 
			{
				j = 0;
			}
			 else 
			{
				int nfSize = newNBuckets;
				newHash = new int[((int) (nfSize) )];
				if (( this.nBuckets < newNBuckets )) 
				{
					K[] k = ((K[]) (((java.lang.Object) (new java.lang.Object[((int) (newNBuckets) )]) )) );
					if (( this._keys != null )) 
					{
						java.lang.System.arraycopy(((java.lang.Object) (this._keys) ), ((int) (0) ), ((java.lang.Object) (k) ), ((int) (0) ), ((int) (this.nBuckets) ));
					}
					
					this._keys = k;
					V[] v = ((V[]) (((java.lang.Object) (new java.lang.Object[((int) (newNBuckets) )]) )) );
					if (( this.vals != null )) 
					{
						java.lang.System.arraycopy(((java.lang.Object) (this.vals) ), ((int) (0) ), ((java.lang.Object) (v) ), ((int) (0) ), ((int) (this.nBuckets) ));
					}
					
					this.vals = v;
				}
				
			}
			
		}
		
		if (( j != 0 )) 
		{
			this.cachedKey = null;
			this.cachedIndex = -1;
			j = -1;
			int nBuckets = this.nBuckets;
			K[] _keys = this._keys;
			V[] vals = this.vals;
			int[] hashes = this.hashes;
			int newMask = ( newNBuckets - 1 );
			while ((  ++ j < nBuckets ))
			{
				int k = 0;
				boolean __temp_stmt150 = false;
				{
					int v = k = hashes[j];
					__temp_stmt150 = ( (( v & -2 )) == 0 );
				}
				
				if ( ! (__temp_stmt150) ) 
				{
					K key = _keys[j];
					V val = vals[j];
					hashes[j] = 1;
					while (true)
					{
						int nProbes = 0;
						int i = ( k & newMask );
						while ( ! ((( newHash[i] == 0 ))) )
						{
							i = ( ( i +  ++ nProbes ) & newMask );
						}
						
						newHash[i] = k;
						boolean __temp_boolv153 = ( i < nBuckets );
						boolean __temp_boolv152 = false;
						if (__temp_boolv153) 
						{
							boolean __temp_stmt154 = false;
							{
								int v = k = hashes[i];
								__temp_stmt154 = ( (( v & -2 )) == 0 );
							}
							
							__temp_boolv152 =  ! (__temp_stmt154) ;
						}
						
						boolean __temp_stmt151 = ( __temp_boolv153 && __temp_boolv152 );
						if (__temp_stmt151) 
						{
							{
								K tmp = _keys[i];
								_keys[i] = key;
								key = tmp;
							}
							
							{
								V tmp = vals[i];
								vals[i] = val;
								val = tmp;
							}
							
							hashes[i] = 1;
						}
						 else 
						{
							_keys[i] = key;
							vals[i] = val;
							break;
						}
						
					}
					
				}
				
			}
			
			if (( nBuckets > newNBuckets )) 
			{
				{
					K[] k = ((K[]) (((java.lang.Object) (new java.lang.Object[((int) (newNBuckets) )]) )) );
					java.lang.System.arraycopy(((java.lang.Object) (_keys) ), ((int) (0) ), ((java.lang.Object) (k) ), ((int) (0) ), ((int) (newNBuckets) ));
					this._keys = k;
				}
				
				{
					V[] v = ((V[]) (((java.lang.Object) (new java.lang.Object[((int) (newNBuckets) )]) )) );
					java.lang.System.arraycopy(((java.lang.Object) (vals) ), ((int) (0) ), ((java.lang.Object) (v) ), ((int) (0) ), ((int) (newNBuckets) ));
					this.vals = v;
				}
				
			}
			
			this.hashes = newHash;
			this.nBuckets = newNBuckets;
			this.nOccupied = this.size;
			this.upperBound = ((int) (( ( newNBuckets * 0.77 ) + .5 )) );
		}
		
	}
	
	
	public   V get(K key)
	{
		int idx = -1;
		if ((logtalk.lang.Runtime.eq(this.cachedKey, key) && ((idx = this.cachedIndex) != -1 ) ))
		{
			return this.vals[idx];
		}
		
		idx = this.lookup(key);
		if (( idx != -1 )) 
		{
			this.cachedKey = key;
			this.cachedIndex = idx;
			return this.vals[idx];
		}
		
		return null;
	}
	
	
	public   java.lang.Object keys()
	{
		logtalk.root.Array<logtalk.ds.ObjectMap> _g1 = new logtalk.root.Array<logtalk.ds.ObjectMap>(new logtalk.ds.ObjectMap[]{((logtalk.ds.ObjectMap) (this) )});
		logtalk.root.Array<java.lang.Object> i = new logtalk.root.Array<java.lang.Object>(new java.lang.Object[]{0});
		logtalk.root.Array<java.lang.Object> len = new logtalk.root.Array<java.lang.Object>(new java.lang.Object[]{this.nBuckets});
		{
			logtalk.lang.Function __temp_odecl155 = new logtalk.ds.ObjectMap_keys_356__Fun<V, K>(((logtalk.root.Array<java.lang.Object>) (i) ), ((logtalk.root.Array<logtalk.ds.ObjectMap>) (_g1) ), ((logtalk.root.Array<java.lang.Object>) (len) ));
			logtalk.lang.Function __temp_odecl156 = new logtalk.ds.ObjectMap_keys_367__Fun<V, K>(((logtalk.root.Array<java.lang.Object>) (i) ), ((logtalk.root.Array<logtalk.ds.ObjectMap>) (_g1) ));
			return new logtalk.lang.DynamicObject(new logtalk.root.Array<java.lang.String>(new java.lang.String[]{"hasNext", "next"}), new logtalk.root.Array<java.lang.Object>(new java.lang.Object[]{__temp_odecl155, __temp_odecl156}), new logtalk.root.Array<java.lang.String>(new java.lang.String[]{}), new logtalk.root.Array<java.lang.Object>(new java.lang.Object[]{}));
		}
		
	}
	
	
	@Override public   double __hx_setField_f(java.lang.String field, double value, boolean handleProperties)
	{
		{
			boolean __temp_executeDef157 = true;
			switch (field.hashCode())
			{
				case 1005083856:
				{
					if (field.equals("cachedIndex")) 
					{
						__temp_executeDef157 = false;
						this.cachedIndex = ((int) (value) );
						return value;
					}
					
					break;
				}
				
				
				case 325636987:
				{
					if (field.equals("nBuckets")) 
					{
						__temp_executeDef157 = false;
						this.nBuckets = ((int) (value) );
						return value;
					}
					
					break;
				}
				
				
				case -553141795:
				{
					if (field.equals("cachedKey")) 
					{
						__temp_executeDef157 = false;
						this.cachedKey = ((K) (((java.lang.Object) (value) )) );
						return value;
					}
					
					break;
				}
				
				
				case 3530753:
				{
					if (field.equals("size")) 
					{
						__temp_executeDef157 = false;
						this.size = ((int) (value) );
						return value;
					}
					
					break;
				}
				
				
				case -1690761732:
				{
					if (field.equals("upperBound")) 
					{
						__temp_executeDef157 = false;
						this.upperBound = ((int) (value) );
						return value;
					}
					
					break;
				}
				
				
				case -394102484:
				{
					if (field.equals("nOccupied")) 
					{
						__temp_executeDef157 = false;
						this.nOccupied = ((int) (value) );
						return value;
					}
					
					break;
				}
				
				
			}
			
			if (__temp_executeDef157) 
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
			boolean __temp_executeDef158 = true;
			switch (field.hashCode())
			{
				case 1005083856:
				{
					if (field.equals("cachedIndex")) 
					{
						__temp_executeDef158 = false;
						this.cachedIndex = ((int) (logtalk.lang.Runtime.toInt(value)) );
						return value;
					}
					
					break;
				}
				
				
				case -1224424900:
				{
					if (field.equals("hashes")) 
					{
						__temp_executeDef158 = false;
						this.hashes = ((int[]) (value) );
						return value;
					}
					
					break;
				}
				
				
				case -553141795:
				{
					if (field.equals("cachedKey")) 
					{
						__temp_executeDef158 = false;
						this.cachedKey = ((K) (value) );
						return value;
					}
					
					break;
				}
				
				
				case 91023059:
				{
					if (field.equals("_keys")) 
					{
						__temp_executeDef158 = false;
						this._keys = ((K[]) (value) );
						return value;
					}
					
					break;
				}
				
				
				case -1690761732:
				{
					if (field.equals("upperBound")) 
					{
						__temp_executeDef158 = false;
						this.upperBound = ((int) (logtalk.lang.Runtime.toInt(value)) );
						return value;
					}
					
					break;
				}
				
				
				case 3612018:
				{
					if (field.equals("vals")) 
					{
						__temp_executeDef158 = false;
						this.vals = ((V[]) (value) );
						return value;
					}
					
					break;
				}
				
				
				case -394102484:
				{
					if (field.equals("nOccupied")) 
					{
						__temp_executeDef158 = false;
						this.nOccupied = ((int) (logtalk.lang.Runtime.toInt(value)) );
						return value;
					}
					
					break;
				}
				
				
				case 325636987:
				{
					if (field.equals("nBuckets")) 
					{
						__temp_executeDef158 = false;
						this.nBuckets = ((int) (logtalk.lang.Runtime.toInt(value)) );
						return value;
					}
					
					break;
				}
				
				
				case 3530753:
				{
					if (field.equals("size")) 
					{
						__temp_executeDef158 = false;
						this.size = ((int) (logtalk.lang.Runtime.toInt(value)) );
						return value;
					}
					
					break;
				}
				
				
			}
			
			if (__temp_executeDef158) 
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
			boolean __temp_executeDef159 = true;
			switch (field.hashCode())
			{
				case 3288564:
				{
					if (field.equals("keys")) 
					{
						__temp_executeDef159 = false;
						return ((logtalk.lang.Function) (new logtalk.lang.Closure(((java.lang.Object) (this) ), logtalk.lang.Runtime.toString("keys"))) );
					}
					
					break;
				}
				
				
				case -1224424900:
				{
					if (field.equals("hashes")) 
					{
						__temp_executeDef159 = false;
						return this.hashes;
					}
					
					break;
				}
				
				
				case 102230:
				{
					if (field.equals("get")) 
					{
						__temp_executeDef159 = false;
						return ((logtalk.lang.Function) (new logtalk.lang.Closure(((java.lang.Object) (this) ), logtalk.lang.Runtime.toString("get"))) );
					}
					
					break;
				}
				
				
				case 91023059:
				{
					if (field.equals("_keys")) 
					{
						__temp_executeDef159 = false;
						return this._keys;
					}
					
					break;
				}
				
				
				case -934437708:
				{
					if (field.equals("resize")) 
					{
						__temp_executeDef159 = false;
						return ((logtalk.lang.Function) (new logtalk.lang.Closure(((java.lang.Object) (this) ), logtalk.lang.Runtime.toString("resize"))) );
					}
					
					break;
				}
				
				
				case 3612018:
				{
					if (field.equals("vals")) 
					{
						__temp_executeDef159 = false;
						return this.vals;
					}
					
					break;
				}
				
				
				case -1097094790:
				{
					if (field.equals("lookup")) 
					{
						__temp_executeDef159 = false;
						return ((logtalk.lang.Function) (new logtalk.lang.Closure(((java.lang.Object) (this) ), logtalk.lang.Runtime.toString("lookup"))) );
					}
					
					break;
				}
				
				
				case 325636987:
				{
					if (field.equals("nBuckets")) 
					{
						__temp_executeDef159 = false;
						return this.nBuckets;
					}
					
					break;
				}
				
				
				case 113762:
				{
					if (field.equals("set")) 
					{
						__temp_executeDef159 = false;
						return ((logtalk.lang.Function) (new logtalk.lang.Closure(((java.lang.Object) (this) ), logtalk.lang.Runtime.toString("set"))) );
					}
					
					break;
				}
				
				
				case 3530753:
				{
					if (field.equals("size")) 
					{
						__temp_executeDef159 = false;
						return this.size;
					}
					
					break;
				}
				
				
				case 1005083856:
				{
					if (field.equals("cachedIndex")) 
					{
						__temp_executeDef159 = false;
						return this.cachedIndex;
					}
					
					break;
				}
				
				
				case -394102484:
				{
					if (field.equals("nOccupied")) 
					{
						__temp_executeDef159 = false;
						return this.nOccupied;
					}
					
					break;
				}
				
				
				case -553141795:
				{
					if (field.equals("cachedKey")) 
					{
						__temp_executeDef159 = false;
						return this.cachedKey;
					}
					
					break;
				}
				
				
				case -1690761732:
				{
					if (field.equals("upperBound")) 
					{
						__temp_executeDef159 = false;
						return this.upperBound;
					}
					
					break;
				}
				
				
			}
			
			if (__temp_executeDef159) 
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
			boolean __temp_executeDef160 = true;
			switch (field.hashCode())
			{
				case 1005083856:
				{
					if (field.equals("cachedIndex")) 
					{
						__temp_executeDef160 = false;
						return ((double) (this.cachedIndex) );
					}
					
					break;
				}
				
				
				case 325636987:
				{
					if (field.equals("nBuckets")) 
					{
						__temp_executeDef160 = false;
						return ((double) (this.nBuckets) );
					}
					
					break;
				}
				
				
				case -553141795:
				{
					if (field.equals("cachedKey")) 
					{
						__temp_executeDef160 = false;
						return ((double) (logtalk.lang.Runtime.toDouble(((java.lang.Object) (this.cachedKey) ))) );
					}
					
					break;
				}
				
				
				case 3530753:
				{
					if (field.equals("size")) 
					{
						__temp_executeDef160 = false;
						return ((double) (this.size) );
					}
					
					break;
				}
				
				
				case -1690761732:
				{
					if (field.equals("upperBound")) 
					{
						__temp_executeDef160 = false;
						return ((double) (this.upperBound) );
					}
					
					break;
				}
				
				
				case -394102484:
				{
					if (field.equals("nOccupied")) 
					{
						__temp_executeDef160 = false;
						return ((double) (this.nOccupied) );
					}
					
					break;
				}
				
				
			}
			
			if (__temp_executeDef160) 
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
			boolean __temp_executeDef161 = true;
			switch (field.hashCode())
			{
				case 3288564:
				{
					if (field.equals("keys")) 
					{
						__temp_executeDef161 = false;
						return this.keys();
					}
					
					break;
				}
				
				
				case 113762:
				{
					if (field.equals("set")) 
					{
						__temp_executeDef161 = false;
						this.set(((K) (dynargs.__get(0)) ), ((V) (dynargs.__get(1)) ));
					}
					
					break;
				}
				
				
				case 102230:
				{
					if (field.equals("get")) 
					{
						__temp_executeDef161 = false;
						return this.get(((K) (dynargs.__get(0)) ));
					}
					
					break;
				}
				
				
				case -1097094790:
				{
					if (field.equals("lookup")) 
					{
						__temp_executeDef161 = false;
						return this.lookup(((K) (dynargs.__get(0)) ));
					}
					
					break;
				}
				
				
				case -934437708:
				{
					if (field.equals("resize")) 
					{
						__temp_executeDef161 = false;
						this.resize(((int) (logtalk.lang.Runtime.toInt(dynargs.__get(0))) ));
					}
					
					break;
				}
				
				
			}
			
			if (__temp_executeDef161) 
			{
				return super.__hx_invokeField(field, dynargs);
			}
			
		}
		
		return null;
	}
	
	
	@Override public   void __hx_getFields(logtalk.root.Array<java.lang.String> baseArr)
	{
		baseArr.push("cachedIndex");
		baseArr.push("cachedKey");
		baseArr.push("upperBound");
		baseArr.push("nOccupied");
		baseArr.push("size");
		baseArr.push("nBuckets");
		baseArr.push("vals");
		baseArr.push("_keys");
		baseArr.push("hashes");
		{
			super.__hx_getFields(baseArr);
		}
		
	}
	
	
}


