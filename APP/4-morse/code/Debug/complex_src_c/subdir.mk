################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../complex_src_c/dictionary.c \
../complex_src_c/morse.c 

OBJS += \
./complex_src_c/dictionary.o \
./complex_src_c/morse.o 

C_DEPS += \
./complex_src_c/dictionary.d \
./complex_src_c/morse.d 


# Each subdirectory must supply rules for building sources it contributes
complex_src_c/%.o: ../complex_src_c/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


