import Axios from "./callerService";

let createActivity = (activity) => {
	return Axios.post("activities", activity);
};

let getActivities = () => {
	return Axios.get("activities");
};

let getActivityById = (id) => {
	return Axios.get(`activities/${id}`);
};

let deleteActivityById = (id) => {
	return Axios.delete(`activities/${id}`);
};

let updateActivityById = (id, activity) => {
	return Axios.put(`activities/${id}`, activity);
};

export const activityService = {
	createActivity,
	getActivities,
	getActivityById,
	deleteActivityById,
	updateActivityById,
};
