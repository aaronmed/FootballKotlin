module.exports = (sequelize, Sequelize) => {
  const Team = sequelize.define("team", {
    name: {
      type: Sequelize.STRING
    },
    stadium: {
      type: Sequelize.STRING
    },
    country: {
      type: Sequelize.STRING
    }
  }, { timestamps: false });
  return Team;
};