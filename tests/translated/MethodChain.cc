#include "MethodChain.h"
#include <iostream>

_MethodChain::_MethodChain(): __vptr(&__vtable){} 

void _MethodChain::__delete(_MethodChain* __this){
delete __this;
}



MethodChain _MethodChain::m(MethodChain __this){
return __this;

}

MethodChain _MethodChain::_m(MethodChain __this, MethodChain a){
return a;

}

int main(int argc, const char* argv[]) {
/*
__rt::Ptr<__rt::Array<String> > args = new __rt::Array<String>(argc-1);
for(int i = 1; i < argc; i++){
(*args)[i-1] = argv[i];
}
*/
MethodChain a;
MethodChain b;
a = new _MethodChain();
b = new _MethodChain();
a->__vptr->_m(a,b);
return 0;
}

_MethodChain_VT _MethodChain::__vtable;

Class _MethodChain::__class() { 
 static Class k = new java::lang::__Class(__rt::literal("MethodChain"), java::lang::__Object::__class());
return k;
}

