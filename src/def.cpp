#include <cstdlib>
#include <string>
#include "def.h"

void Value::free() {
    if (pointer) {
        switch(v_type){
            case V_NUM:
                std::free(pointer);
                break;
            case V_STRING:
                ((string*)pointer)->~string();
                std::free(pointer);
                break;
            default:
                std::free(pointer);
                break;
        }
    }
}

Value::Value(u32 type, void *p):pointer(p), v_type(type) {
}

Value::~Value() {
    free();
}

void *Value::get() const {
    return pointer;
}

void Value::set(void* p) {
    pointer = p;
}

u32 Value::getType() const {
    return v_type;
}

