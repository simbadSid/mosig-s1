################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../src_c/dictionary.c \
../src_c/morse.c 

OBJS += \
./src_c/dictionary.o \
./src_c/morse.o 

C_DEPS += \
./src_c/dictionary.d \
./src_c/morse.d 


# Each subdirectory must supply rules for building sources it contributes
src_c/%.o: ../src_c/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


