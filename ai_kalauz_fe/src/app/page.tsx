"use client";

import { useRouter } from "next/navigation";

export default function HomePage() {
  const router = useRouter();

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
      <div className="flex flex-col items-center mb-10">
        {/* Replace with your own logo image later */}
        <div className="text-6xl font-extrabold text-blue-600 mb-4">
          🤖 AI Kalauz
        </div>
        <p className="text-gray-600 text-lg text-center max-w-md">
          Tanulj okosabban a AI által vezérelt oktatóddal.
        </p>
      </div>

      {/* Buttons */}
      <div className="flex flex-col gap-4 w-40">
        <button
          onClick={() => router.push("/login")}
          className="bg-blue-600 text-white py-2 px-4 rounded-lg shadow hover:bg-blue-700 transition"
        >
          Login
        </button>
        <button
          onClick={() => router.push("/register")}
          className="bg-gray-200 text-gray-800 py-2 px-4 rounded-lg shadow hover:bg-gray-300 transition"
        >
          Register
        </button>
      </div>
    </div>
  );
}