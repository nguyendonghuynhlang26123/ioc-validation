package validator.impl;

import validator.ChainPrototype;

import java.util.HashMap;
import java.util.Map;

public class ChainRegistry {
    private static final ChainRegistry INSTANCE = new ChainRegistry();

    private ChainRegistry(){}

    public static ChainRegistry getInstance() {
        return INSTANCE;
    }

    private final Map<String, ChainPrototype> cache = new HashMap<>();

    public void register(String key, ChainPrototype chain){
        cache.put(key, chain);
    }

    public boolean hasChain(String key){return cache.containsKey(key);}

    public ChainPrototype getChain(String key){
        if(cache.containsKey(key)){
            return cache.get(key).cloneChain();
        }
        return null;
    }
}
