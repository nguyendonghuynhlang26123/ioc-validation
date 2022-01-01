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
import validator.impl.*;

import java.util.EmptyStackException;
import java.util.Stack;

public class BaseValidateHolderBuilder<T> implements ValidateHolderBuilder<T> {
    BaseValidatorHolder<T> validateHolder;
    ChainPrototype<T> rootChain;
    ChainPrototype currentChain;
    Stack<ChainPrototype> compositeStack;

    public BaseValidateHolderBuilder(ViolationHandler handler) {
        validateHolder = new BaseValidatorHolder<>(handler);
        compositeStack = new Stack<>();
    }

    @Override
    public ValidateHolderBuilder<T> applyConstraint(){
        currentChain = new ValidatorChain<>();
        addChainNoField();
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyConstraint(String field){
        currentChain = new ValidatorChain<>();
        addCurrentToCompositeStack(field);
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyPojoConstraint(){
        currentChain = new PojoValidatorChain<>();
        addChainNoField();
        compositeStack.push(currentChain);
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyPojoConstraint(String field){
        currentChain = new PojoValidatorChain<>();
        addCurrentToCompositeStack(field);
        compositeStack.push(currentChain);
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> endCompositeConstraint(){
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
    public ValidateHolderBuilder<T> applyCollectionInternalConstraint() {
        currentChain = new CollectionInternalValidatorChain<>();
        addChainNoField();
        compositeStack.push(currentChain);
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyCollectionInternalConstraint(String field) {
        currentChain = new CollectionInternalValidatorChain<>();
        addCurrentToCompositeStack(field);
        compositeStack.push(currentChain);
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyCollectionConstraint(){
        currentChain = new CollectionValidatorChain<>();
        addChainNoField();
        compositeStack.push(currentChain);
        return this;
    }

    @Override
    public ValidateHolderBuilder<T> applyCollectionConstraint(String field){
        currentChain = new CollectionValidatorChain<>();
        addCurrentToCompositeStack(field);
        compositeStack.push(currentChain);
        return this;
    }

    private void addChainNoField(){
        if(rootChain==null){
            rootChain = currentChain;
        } else {
            addCurrentToCompositeStack();
        }
    }

    private void addCurrentToCompositeStack(){
        try {
            ChainPrototype composite = compositeStack.peek();
            composite.addChain(new AddChainRequest(currentChain));
        } catch (NoSuchMethodException e) {
            throw new ChainBuilderException("Parent required chain with field name");
        } catch (EmptyStackException emptyStackException){
            throw new ChainBuilderException("Chain not in a composite parent scope");
        }
    }

    private void addCurrentToCompositeStack(String field){
        try{
            ChainPrototype composite = compositeStack.peek();
            composite.addChain(new AddChainRequest(field, currentChain));
        } catch (EmptyStackException empty){
            addCurrentToCompositeRoot(field);
        } catch (NoSuchMethodException noSuchMethodException){
            throw new ChainBuilderException("Parent is not pojo chain");
        }
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

    protected void addValidatorToCurrent(Validator validator){
        try {
            currentChain.appendValidator(validator);
        } catch (NoSuchMethodException e) {
            throw new ChainBuilderException("Add validator not in single chain scope at "
                    + validator.getClass().getSimpleName());
        }
    }

    @Override
    public ValidatorHolder<T> buildValidatable(){
        validateHolder.setChain(rootChain);
        return validateHolder;
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
