#ifndef _DEF_H_
#define _DEF_H_

#include <cstdlib>

typedef unsigned long long  u64;
typedef unsigned long       u32;
typedef unsigned short      u16;
typedef unsigned char       u8;
typedef long long  i64;
typedef long       i32;
typedef short      i16;
typedef char       i8;

using namespace std;

typedef enum ValueType {
    V_UNDEFINED = 0,
    V_NUM,              // i64
    V_STRING            // string
} ValueType;

// Uniform Value Class
// Store a pointer to the origin data
class Value {
    private:
        void    *pointer;
        u32     v_type;
        void    free();
    public:
                Value(u32 type, void* p=NULL);
               ~Value();
        void    *get() const;
        void    set(void*);
        u32     getType() const;
};

#endif // _DEF_H_
