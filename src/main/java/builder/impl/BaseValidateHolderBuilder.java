package builder.impl;

import annotations.impl.LengthValidator;
import annotations.impl.MaxValidator;
import annotations.impl.NotEmptyValidator;
import builder.ValidateHolderBuilder;
import handler.ViolationHandler;
import utils.AddChainRequest;
import utils.exceptions.ChainBuilderException;
import validator.ChainPrototype;
import validator.Validator;
import validator.ValidatorHolder;
import validator.impl.BaseValidatorHolder;
import validator.impl.CollectionInternalValidatorChain;
import validator.impl.PojoValidatorChain;
import validator.impl.ValidatorChain;

import java.util.EmptyStackException;
import java.util.Stack;

public class BaseValidateHolderBuilder<T> implements ValidateHolderBuilder<T> {
    BaseValidatorHolder<T> validateHolder;
    ChainPrototype rootChain;
    ChainPrototype currentChain;
    Stack<ChainPrototype> compositeStack;

    public BaseValidateHolderBuilder(ViolationHandler handler) {
        validateHolder = new BaseValidatorHolder<>(handler);
        compositeStack = new Stack<>();
    }

    private void addChainNoField(ChainPrototype chain){
        if(rootChain==null){
            rootChain = chain;
            currentChain = rootChain;
        } else {
            try {
                currentChain.addChain(new AddChainRequest(chain));
            } catch (NoSuchMethodException e) {
                throw new ChainBuilderException("Parent required chain with field name");
            }
        }
    }

    @Override
    public ValidateHolderBuilder<T> applyConstraint(){
//        currentChain = new ValidatorChain();
        addChainNoField(new ValidatorChain());
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyConstraint(String field){
        currentChain = new ValidatorChain();
        try{
            ChainPrototype composite = compositeStack.peek();
            composite.addChain(new AddChainRequest(field, currentChain));
        } catch (EmptyStackException empty){
            addCurrentToCompositeRoot(field);
        } catch (NoSuchMethodException noSuchMethodException){
            throw new ChainBuilderException("Parent single");
        }
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyPojoConstraints(){
        ChainPrototype newChain = new PojoValidatorChain<>();
        addChainNoField(newChain);
        compositeStack.push(newChain);
        currentChain = newChain;
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyPojoConstraints(String field){
        currentChain = new PojoValidatorChain();
        ChainPrototype composite = compositeStack.peek();
        try {
            composite.addChain(new AddChainRequest(field, currentChain));
        } catch (NoSuchMethodException e) {
            throw new ChainBuilderException("Parent not composite");
        }
        compositeStack.push(currentChain);
//        addCurrentToCompositeRoot(field);
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> endPojoConstraint(){
        if(!compositeStack.isEmpty()){
            compositeStack.pop();
            try{
                currentChain = compositeStack.peek();
            } catch (EmptyStackException e){
                currentChain = rootChain;
            }
        }
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyCollectionConstraints() {
//        currentChain = new CollectionValidatorChain();
        addChainNoField(new CollectionInternalValidatorChain<>());
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyCollectionConstraints(String field) {
        currentChain = new CollectionInternalValidatorChain<>();
        ChainPrototype composite = compositeStack.peek();
        try {
            composite.addChain(new AddChainRequest(field, currentChain));
        } catch (NoSuchMethodException e) {
            throw new ChainBuilderException("Parent single");
        }
//        addCurrentToCompositeRoot(field);
        return this;
    }

    private void addCurrentToCompositeRoot(String field){
        if (rootChain == null){
            rootChain = new PojoValidatorChain<>();
        }
        try {
            rootChain.addChain(new AddChainRequest(field, currentChain));
        } catch (NoSuchMethodException e) {
            throw new ChainBuilderException("Parent chain is a single chain");
        }
    }

    @Override
    public ValidatorHolder<T> buildValidatable(){
        validateHolder.setChain(rootChain);
        return validateHolder;
    }

    public ValidateHolderBuilder<T> setChain(ChainPrototype<T> chain){
        return this;
    }

    protected void addValidatorToCurrent(Validator validator){
        try {
            currentChain.appendValidator(validator);
        } catch (NoSuchMethodException e) {
            throw new ChainBuilderException("Add validator not in single chain scope at "
                    + validator.getClass().getSimpleName());
        }
    }

    // Others
    @Override
    public ValidateHolderBuilder<T> handler(ViolationHandler handler){
        validateHolder.setHandler(handler);
        return this;
    }

    // ---------------------- Constraints ---------------------------------------

    @Override
    public ValidateHolderBuilder<T> notEmpty(){
        this.addValidatorToCurrent(new NotEmptyValidator());
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> length(int min, int max){
        this.addValidatorToCurrent(new LengthValidator(min, max));
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> length(int max){
        this.addValidatorToCurrent(new LengthValidator(0, max));
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> max(int max){
        this.addValidatorToCurrent(new MaxValidator(max));
        return this;
    }

    //Customize validator
    @Override
    public ValidateHolderBuilder<T> addCustomValidator(Validator validator){
        this.addValidatorToCurrent(validator);
        return this;
    }
}
