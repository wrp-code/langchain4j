from modelscope import AutoModelForCausalLM, AutoTokenizer, GenerationConfig

model_path = ''

model = AutoModelForCausalLM.from_pretrained(model_path, device_map='auto')

tokenizer = AutoTokenizer.from_pretrained(model_path)

gen_config = GenerationConfig.from_pretrained(model_path)

print( tokenizer('你好，are you ok'))
tokenizer.decode(108386)


def run_prompt(prompt,temperature=0.1,top_k=20,top_p=0.8, max_new_tokens=2848):
    gen_config.temperature =temperature
    gen_config.top_k= top_k
    gen_config.top_p= top_p
    messages = [
        {"role":"system", "content":""},
        {"role":"user", "content": prompt}
    ]

    text = tokenizer.apply_chat_template(
        messages,
        tokenize=False,
        add_generation_prompt=True
    )
    print(text)
    model_input = tokenizer([text], return_tensors="pt").to('cuda')

    generated_ids = model.generate(model_input.input_ids,
                   max_new_tokens= max_new_tokens,
                   generation_config=gen_config)

    generated_ids = [output_ids[len(input_ids):] for input_ids, output_ids in zip(model_input.input_ids, generated_ids)]
    response = tokenizer.batch_decode(generated_ids, skip_special_tokens=True)[0]
    return response