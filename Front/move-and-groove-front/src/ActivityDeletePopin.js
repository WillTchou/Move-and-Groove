import React from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";
import { activityService } from "./activityService";

export const ActivityDeletePopin = ({ show, handleClose, activity }) => {
	const deleteActivity = (e) => {
		e.preventDefault();
		activityService
			.deleteActivityById(activity.id)
			.then(() => window.location.reload());
	};

	return (
		<Modal show={show} onHide={handleClose}>
			<Modal.Header closeButton>
				<Modal.Title>Delete Activity</Modal.Title>
			</Modal.Header>
			<Modal.Body>
				Are you sure to delete the activity: {activity.name}?
			</Modal.Body>
			<Modal.Footer>
				<Button variant="secondary" onClick={handleClose}>
					Close
				</Button>
				<Button variant="primary" onClick={deleteActivity}>
					Save Changes
				</Button>
			</Modal.Footer>
		</Modal>
	);
};
