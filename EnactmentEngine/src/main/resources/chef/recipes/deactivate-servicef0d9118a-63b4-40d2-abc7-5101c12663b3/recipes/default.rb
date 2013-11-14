#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-servicef0d9118a-63b4-40d2-abc7-5101c12663b3" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicef0d9118a-63b4-40d2-abc7-5101c12663b3::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/f0d9118a-63b4-40d2-abc7-5101c12663b3.war]", :immediately
end

ruby_block "remove-servicef0d9118a-63b4-40d2-abc7-5101c12663b3" do
  block do
  	node.run_list.remove("recipe[servicef0d9118a-63b4-40d2-abc7-5101c12663b3::war]")
  end
  only_if { node.run_list.include?("recipe[servicef0d9118a-63b4-40d2-abc7-5101c12663b3::war]") }
end

ruby_block "remove-deactivate-servicef0d9118a-63b4-40d2-abc7-5101c12663b3" do
  block do
    node.run_list.remove("recipe[deactivate-servicef0d9118a-63b4-40d2-abc7-5101c12663b3]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef0d9118a-63b4-40d2-abc7-5101c12663b3]") }
end
