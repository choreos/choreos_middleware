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

ruby_block "remove-servicef297d74d-e01c-414c-88c1-a0b9bb07f54b" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicef297d74d-e01c-414c-88c1-a0b9bb07f54b::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/f297d74d-e01c-414c-88c1-a0b9bb07f54b.war]", :immediately
end

ruby_block "remove-servicef297d74d-e01c-414c-88c1-a0b9bb07f54b" do
  block do
  	node.run_list.remove("recipe[servicef297d74d-e01c-414c-88c1-a0b9bb07f54b::war]")
  end
  only_if { node.run_list.include?("recipe[servicef297d74d-e01c-414c-88c1-a0b9bb07f54b::war]") }
end

ruby_block "remove-deactivate-servicef297d74d-e01c-414c-88c1-a0b9bb07f54b" do
  block do
    node.run_list.remove("recipe[deactivate-servicef297d74d-e01c-414c-88c1-a0b9bb07f54b]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicef297d74d-e01c-414c-88c1-a0b9bb07f54b]") }
end
