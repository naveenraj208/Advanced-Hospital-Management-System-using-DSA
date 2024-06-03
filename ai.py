import os
import google.generativeai as genai


genai.configure(api_key="API_KEY")


generation_config = {
    "temperature": 0.9,
    "top_p": 1,
    "top_k": 0,
    "max_output_tokens": 2048,
    "response_mime_type": "text/plain",
}

safety_settings = [
    {"category": "HARM_CATEGORY_HARASSMENT", "threshold": "BLOCK_MEDIUM_AND_ABOVE"},
    {"category": "HARM_CATEGORY_HATE_SPEECH", "threshold": "BLOCK_MEDIUM_AND_ABOVE"},
    {"category": "HARM_CATEGORY_SEXUALLY_EXPLICIT", "threshold": "BLOCK_MEDIUM_AND_ABOVE"},
    {"category": "HARM_CATEGORY_DANGEROUS_CONTENT", "threshold": "BLOCK_MEDIUM_AND_ABOVE"},
]

# Initialize the GenerativeModel
model = genai.GenerativeModel(
    model_name="gemini-1.0-pro-001",
    safety_settings=safety_settings,
    generation_config=generation_config,
)


def handle_input(user_input):

    response = chat_session.send_message(user_input)

    chat_session.history.append({"user": user_input, "model": response.text})
    return response.text


chat_session = model.start_chat(history=[])


while True:
    user_input = input("You: ")
    if user_input.lower() == "exit":
        print("Goodbye!")
        break
    else:
        bot_response = handle_input(user_input)
        print("Chatbot:", bot_response)
