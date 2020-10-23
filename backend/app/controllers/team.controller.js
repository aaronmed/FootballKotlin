const db = require("../models");
const Teams = db.teams;
const Op = db.Sequelize.Op;

// Create and Save a new Team
exports.create = (req, res) => {
  // Validate request
  if (!req.body.name) {
    res.status(400).send({
      message: "Content can not be empty!"
    });
    return;
  }

  // Create a Team
  const team = {
    name: req.body.name,
    stadium: req.body.stadium,
    country: req.body.country
  };

  // Save Team in the database
  Teams.create(team)
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while creating the Tutorial."
      });
    });
};

// Retrieve all Tutorials from the database.
exports.findAll = (req, res) => {
  Teams.findAll()
    .then(data => {
      res.send(data);
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while retrieving ids"
      });
    });
};

// Find a single Team with an id
exports.findOne = (req, res) => {
  let id = req.params.id;
  Teams.findByPk(id)
    .then(data => {
      if (!data) {
        res.status(400).send({
          message:
            "No Bicycle found with that id"
        })
      }
      res.send(data);
      return;
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error ocurre while retrieving team id"
      });
      return;
    });
};

// Update a Team by the id in the request
exports.update = (req, res) => {
  const id = req.params.id;

  Teams.update(req.body, {
    where: { id: id }
  })
    .then(num => {
      if (num == 1) {
        res.send({
          message: "Team was updated successfully."
        });
      } else {
        res.send({
          message: `Cannot update Team with id=${id}. Maybe Team was not found or req.body is empty!`
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Error updating Team with id=" + id
      });
    });
};

// Delete a Team with the specified id in the request
exports.delete = (req, res) => {
  const id = req.params.id;

  Teams.destroy({
    where: { id: id }
  })
    .then(num => {
      if (num == 1) {
        res.send({
          message: "Team was deleted successfully!"
        });
      } else {
        res.send({
          message: `Cannot delete Team with id=${id}. Maybe Team was not found!`
        });
      }
    })
    .catch(err => {
      res.status(500).send({
        message: "Could not delete Team with id=" + id
      });
    });
};

// Delete all Teams from the database.
exports.deleteAll = (req, res) => {
  Teams.destroy({
    where: {},
    truncate: false
  })
    .then(nums => {
      res.send({ message: `${nums} Teams were deleted successfully!` });
    })
    .catch(err => {
      res.status(500).send({
        message:
          err.message || "Some error occurred while removing all Teams."
      });
    });
};
