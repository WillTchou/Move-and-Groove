import "./App.css";
import { Home } from "./Home";
import { SignInForm } from "./SignInForm";
import { SignUpForm } from "./SignUpForm";
import { MyAccount } from "./MyAccount";
import { BrowserRouter, Route, Routes } from "react-router-dom";

function App() {
	return (
		<BrowserRouter>
			<Routes>
				<Route path="/" element={<Home />} />
				<Route path="/myAccount" element={<MyAccount />} />
				<Route path="/signIn" element={<SignInForm />} />
				<Route path="/signUp" element={<SignUpForm />} />
			</Routes>
		</BrowserRouter>
	);
}

export default App;
