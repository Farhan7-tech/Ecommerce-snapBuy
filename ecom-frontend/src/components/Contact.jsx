import { FaEnvelope, FaMapMarkedAlt, FaPhone } from "react-icons/fa";

const Contact = () => {
  return (
    <div
      className="flex flex-col items-center justify-center min-h-screen py-12 bg-fixed bg-cover bg-center"
      style={{
        backgroundImage:
          "url('https://images.unsplash.com/photo-1533035353720-f1c6a75cd8ab?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D')",
      }}
    >
      <div className="bg-white/80 backdrop-blur-md shadow-2xl rounded-2xl p-10 w-full max-w-lg border border-gray-200">
        <h1 className="text-4xl font-bold text-center text-gray-800 mb-6">
          Contact Us
        </h1>
        <p className="text-gray-700 text-center mb-6">
          We’d love to hear from you! Fill out the form or contact us directly.
        </p>

        <form className="space-y-5">
          <div>
            <label className="block text-sm font-medium text-gray-800">
              Name
            </label>
            <input
              type="text"
              required
              className="mt-1 block w-full border border-gray-300 rounded-lg p-3 text-gray-900 focus:ring-2 focus:ring-blue-500 focus:outline-none"
              placeholder="John Doe"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-800">
              Email
            </label>
            <input
              type="email"
              required
              className="mt-1 block w-full border border-gray-300 rounded-lg p-3 text-gray-900 focus:ring-2 focus:ring-blue-500 focus:outline-none"
              placeholder="you@example.com"
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-800">
              Message
            </label>
            <textarea
              rows="4"
              required
              className="mt-1 block w-full border border-gray-300 rounded-lg p-3 text-gray-900 focus:ring-2 focus:ring-blue-500 focus:outline-none"
              placeholder="Type your message..."
            />
          </div>

          <button className="w-full bg-gradient-to-r from-blue-500 to-indigo-600 text-white py-3 rounded-lg font-medium hover:shadow-lg transition duration-300">
            Send Message
          </button>
        </form>

        <div className="mt-8 text-center">
          <h2 className="text-lg font-semibold text-gray-800">
            Contact Information
          </h2>
          <div className="flex flex-col items-center space-y-3 mt-4 text-gray-700">
            <div className="flex items-center">
              <FaPhone className="text-blue-500 mr-2" />
              <span>+7302726588</span>
            </div>

            <div className="flex items-center">
              <FaEnvelope className="text-blue-500 mr-2" />
              <span>atreus396@gmail.com</span>
            </div>

            <div className="flex items-center">
              <FaMapMarkedAlt className="text-blue-500 mr-2" />
              <span>C-497, Alpha-1, Greater Noida</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Contact;
