#include "Pt.h"
#include <iostream>

_Pt::_Pt() : __vptr(&__vtable){
}

Class _Pt::__class() {
      static Class k =
        new __Class(__rt::literal("Pt"), __Object::__class());
      return k;
}

_Pt_VT _Pt::__vtable;

int main(void)
{
  std::cout << "HELLO WORLD MOTHERFUCKER"<<std::endl;

  Pt p = new _Pt();

  std::cout << "p.toString(p)  : "
	    << p->__vptr->toString(p)->data 
	    << std::endl;
    return 0;
}
